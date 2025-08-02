package org.clinic.notification_service.kafka;

import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.model.sql.OutboxMessageEntity;

public interface KafkaProducerService {
    void sendNotification(NotificationEvent event);
    void sendNotification(OutboxMessageEntity outbox);
}