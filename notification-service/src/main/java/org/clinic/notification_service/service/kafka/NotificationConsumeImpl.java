package org.clinic.notification_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.clinic.notification_service.enums.InboxStatus;
import org.clinic.notification_service.enums.NotificationStatus;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.model.sql.InboxEventEntity;
import org.clinic.notification_service.repository.InboxEventRepository;
import org.clinic.notification_service.service.NotificationDirectService;
import org.clinic.notification_service.service.NotificationService;
import org.clinic.notification_service.service.kafka.NotificationConsumer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumeImpl implements NotificationConsumer {
    private final NotificationDirectService notificationDirectService;
    private final InboxEventRepository inboxEventRepository;
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic.notification.name:notification}", groupId = "${kafka.topic.notification.group:notification}")
    public void consume(NotificationEvent event) {
        log.info("Received notification event: {}", event);
        notificationDirectService.directSend(event);
    }


    @KafkaListener(topics = "${kafka.topic.notification.name:notification}", groupId = "${kafka.topic.notification.group:notification-groups}")
    public void consume(
            ConsumerRecord<String, String> record,
            Acknowledgment ack
    ) throws JsonProcessingException {
        String headerId = getHeader(record, "aggregateId");
        UUID aggregateId = headerId != null ? UUID.fromString(headerId) : UUID.nameUUIDFromBytes(record.value().getBytes());
        String eventType = getHeader(record, "eventType");
        String payload = record.value();
        if (eventType == null) eventType = "UNKNOWN";
        // 2) Insert vào inbox (RECEIVED). Nếu đã tồn tại -> skip xử lý (idempotent)
        InboxEventEntity ibE = new InboxEventEntity(aggregateId, payload, InboxStatus.RECEIVED);
        try {
            inboxEventRepository.save(ibE);
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
            ibE.setStatus(InboxStatus.PROCESSED);
            ibE.setProcessedAt(Instant.now());
            inboxEventRepository.save(ibE);
            // 6) Commit offset sau khi DB commit thành công (vì @Transactional)
            ack.acknowledge();

        } catch (Exception ex) {
            // 7) Đánh dấu FAILED + tăng retries (để retry về sau hoặc DLT)
            var e = inboxEventRepository.findByAggregateId(aggregateId).orElseThrow();
            e.setStatus(InboxStatus.FAILED);
            e.setRetries(e.getRetries() + 1);
            inboxEventRepository.save(e);

            // ném RuntimeException để Spring Kafka trigger retry/DLT theo config (nếu dùng DefaultErrorHandler)
            throw ex;
        }
    }

    private String getHeader(ConsumerRecord<String, String> record, String key) {
        var headers = record.headers().headers(key);
        var it = headers.iterator();
        return it.hasNext() ? new String(it.next().value()) : null;
    }
}
