package org.clinic.notification_service.service.sender;

import org.clinic.notification_service.model.NotificationEvent;

public interface PushNotificationSender {
    void send(NotificationEvent event);
}
