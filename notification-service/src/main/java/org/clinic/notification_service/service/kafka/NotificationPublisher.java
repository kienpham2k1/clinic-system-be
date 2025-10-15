package org.clinic.notification_service.service.kafka;

import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.model.sql.OutboxEventEntity;

import java.util.UUID;

public interface NotificationPublisher {
    void publishNotificationEvent(NotificationEvent event);

    void publishNotificationEvent(OutboxEventEntity event, UUID aggregateId);
}