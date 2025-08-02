package org.clinic.notification_service.kafka;

import org.clinic.notification_service.model.NotificationEvent;

public interface KafkaProducerService {
    void sendNotification(NotificationEvent event);
}