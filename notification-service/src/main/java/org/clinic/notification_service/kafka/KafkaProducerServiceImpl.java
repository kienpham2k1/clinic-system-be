package org.clinic.notification_service.kafka;

import lombok.RequiredArgsConstructor;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.model.sql.OutboxMessageEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final KafkaTemplate<String, NotificationEvent> kafkaTemplateNotification;
    private final KafkaTemplate<String, String> kafkaTemplateOutbox;

    @Override
    public void sendNotification(NotificationEvent event) {
        kafkaTemplateNotification.send("notification-topic", event);
    }

    @Override
    public void sendNotification(OutboxMessageEntity outbox) {
        kafkaTemplateOutbox.send("notification-events", outbox.getId().toString(), outbox.getPayload());
    }
}