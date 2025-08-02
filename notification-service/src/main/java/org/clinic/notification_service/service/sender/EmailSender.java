package org.clinic.notification_service.service.sender;

import org.clinic.notification_service.model.NotificationEvent;

public interface EmailSender {
    void send(NotificationEvent event);
}
