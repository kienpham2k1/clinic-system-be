package org.clinic.notification_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.model.sql.InboxMessageEntity;
import org.clinic.notification_service.repository.InboxMessageRepository;
import org.clinic.notification_service.service.NotificationActService;
import org.springframework.kafka.annotation.KafkaListener;
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
    public void listen(
            @Header(KafkaHeaders.RECEIVED_KEY) UUID messageId,
            String message
    ) throws JsonProcessingException {
        if (inboxMessageRepository.existsByMessageId(messageId)) {
            log.info("Message {} already processed");
            return;
        }
        NotificationEvent event = objectMapper.readValue(message, NotificationEvent.class);
        notificationActService.act(event);
        inboxMessageRepository.save(InboxMessageEntity.builder()
                .messageId(messageId)
                .payload(message)
                .build());
    }
}
