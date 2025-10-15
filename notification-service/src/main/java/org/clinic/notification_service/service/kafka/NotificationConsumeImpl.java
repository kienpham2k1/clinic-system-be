package org.clinic.notification_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.clinic.notification_service.enums.InboxStatus;
import org.clinic.notification_service.enums.NotificationStatus;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.model.sql.DltEventEntity;
import org.clinic.notification_service.model.sql.InboxEventEntity;
import org.clinic.notification_service.repository.DltEventRepository;
import org.clinic.notification_service.repository.InboxEventRepository;
import org.clinic.notification_service.service.NotificationDirectService;
import org.clinic.notification_service.service.NotificationService;
import org.clinic.notification_service.service.kafka.NotificationConsumer;
import org.clinic.notification_service.utils.KafkaHeaderUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumeImpl implements NotificationConsumer {
    private final NotificationDirectService notificationDirectService;
    private final InboxEventRepository inboxEventRepository;
    private final DltEventRepository repo;
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

//    @KafkaListener(topics = "${app.kafka.notification.topic:notification}", groupId = "${app.kafka.notification.group:notification-groups}")
//    public void consume(NotificationEvent event) {
//        log.info("Received notification event: {}", event);
//        notificationDirectService.directSend(event);
//    }

    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 500, multiplier = 2.0, maxDelay = 15000),
            dltTopicSuffix = ".dlt"
    )
    @KafkaListener(topics = "${app.kafka.notification.topic:notification}", groupId = "${app.kafka.notification.group:notification-groups}")
//    @Transactional
    public void consume(
            ConsumerRecord<String, String> record,
            Acknowledgment ack
    ) throws JsonProcessingException {
        log.info("Received notification record: {}", record);
        String headerId = KafkaHeaderUtils.getHeader(record, "aggregateId");
        UUID aggregateId = headerId != null ? UUID.fromString(headerId) : UUID.nameUUIDFromBytes(record.value().getBytes());
        String eventType = KafkaHeaderUtils.getHeader(record, "eventType");
        String payload = record.value();
        if (eventType == null) eventType = "UNKNOWN";
        // 2) Insert vào inbox (RECEIVED). Nếu đã tồn tại -> skip xử lý (idempotent)
        Optional<InboxEventEntity> exists = inboxEventRepository.findByAggregateId(aggregateId);
        try {
            if (exists.isPresent() && exists.get().getStatus().equals(InboxStatus.PROCESSED)) {
                throw new DataIntegrityViolationException("Duplicate key found in inbox event");
            } else if (exists.isEmpty()) {
                InboxEventEntity ibE = new InboxEventEntity(aggregateId, payload, InboxStatus.RECEIVED);
                inboxEventRepository.save(ibE);
            }
        } catch (DataIntegrityViolationException dup) {
            ack.acknowledge();
            return;
        }

        // 3) Xử lý nghiệp vụ
        try {
            NotificationEvent notificationEvent = objectMapper.readValue(payload, NotificationEvent.class);
            notificationDirectService.directSend(notificationEvent);

            // 4) Đánh dấu PROCESSED
            notificationService.updateStatus(aggregateId, NotificationStatus.SENT, Instant.now(), 0);
            // 5) Update Inbox event
            exists = inboxEventRepository.findByAggregateId(aggregateId);
            exists.get().setStatus(InboxStatus.PROCESSED);
            exists.get().setProcessedAt(Instant.now());
            inboxEventRepository.save(exists.get());
            // 6) Commit offset sau khi DB commit thành công (vì @Transactional)
            ack.acknowledge();

        } catch (Exception ex) {
            // 7) Đánh dấu FAILED + tăng retries (để retry về sau hoặc DLT)
            var e = inboxEventRepository.findByAggregateId(aggregateId).orElseThrow();
            e.setStatus(InboxStatus.FAILED);
            e.setRetries(e.getRetries() + 1);
            e.setProcessedAt(Instant.now());
            inboxEventRepository.save(e);
            log.error("Error while consume notification event", ex);

            // ném RuntimeException để Spring Kafka trigger retry/DLT theo config (nếu dùng DefaultErrorHandler)
            throw ex;
        }
    }

    @DltHandler // xử lý message ở DLT
    public void onDlt(
            ConsumerRecord<String, String> record,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            Acknowledgment ack) {
        // Tại đây có thể log, lưu DB, hoặc gọi service lưu DltEvent như ở trên
        log.info("Received notification record: {}", record);
        try {
            DltEventEntity e = new DltEventEntity();
            e.setOriginalTopic(KafkaHeaderUtils.getHeader(record, KafkaHeaders.ORIGINAL_TOPIC));
            e.setOriginalPartition(KafkaHeaderUtils.getIntHeader(record, KafkaHeaders.ORIGINAL_PARTITION));
            e.setOriginalOffset(KafkaHeaderUtils.getLongHeader(record, KafkaHeaders.ORIGINAL_OFFSET));
            e.setExceptionClass(KafkaHeaderUtils.getHeader(record, KafkaHeaders.EXCEPTION_FQCN));
            e.setExceptionMessage(KafkaHeaderUtils.getHeader(record, KafkaHeaders.EXCEPTION_MESSAGE));
            e.setStacktrace(KafkaHeaderUtils.getHeader(record, KafkaHeaders.EXCEPTION_STACKTRACE));
            e.setKey(record.key());
            e.setPayload(record.value());

            repo.save(e);
            ack.acknowledge();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
