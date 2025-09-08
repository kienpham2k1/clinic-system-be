package org.clinic.notification_service.service;

import org.clinic.notification_service.model.NotificationEvent;

public interface EmailSender {
    void send(NotificationEvent event);
}
