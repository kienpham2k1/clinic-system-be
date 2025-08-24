package org.clinic.notification_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.clinic.common_service_web.exception.NotFoundException;
import org.clinic.notification_service.enums.InboxStatus;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.model.sql.InboxMessageEntity;
import org.clinic.notification_service.repository.InboxMessageRepository;
import org.clinic.notification_service.service.NotificationActService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaNotificationListenerImpl implements KafkaNotificationListener {
    private final NotificationActService notificationActService;
    private final InboxMessageRepository inboxMessageRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void listen(NotificationEvent event) {
        log.info("Received notification event: {}", event);
        notificationActService.act(event);
    }


    @KafkaListener(topics = "notification-events", groupId = "notification-group")
    @Transactional
    public void listen(
            @Header(KafkaHeaders.RECEIVED_KEY) UUID messageId,
            String message,
            Acknowledgment ack
    ) throws JsonProcessingException {
        if (inboxMessageRepository.existsByMessageId(messageId)) {
            log.info("Message {} already processed");
            ack.acknowledge();
            return;
        }
        //lưu vào db
        inboxMessageRepository.save(InboxMessageEntity.builder()
                .messageId(messageId)
                .payload(message)
                .status(InboxStatus.RECEIVED)
                .build());
        NotificationEvent event = objectMapper.readValue(message, NotificationEvent.class);

        try {
            notificationActService.act(event);
            inboxMessageRepository.save(InboxMessageEntity.builder()
                    .messageId(messageId)
                    .payload(message)
                    .status(InboxStatus.PROCESSED)
                    .build());
        } catch (Exception ex) {
            var e = inboxMessageRepository.findByMessageId(messageId).orElseThrow(() -> new NotFoundException(""));
            e.setStatus(InboxStatus.FAILED);
            //e.setRetries(e.getRetries() + 1);
            inboxMessageRepository.save(e);
            throw ex;
        }


    }
}
