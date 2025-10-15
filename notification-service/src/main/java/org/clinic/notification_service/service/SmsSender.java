package org.clinic.notification_service.service;

import org.clinic.notification_service.model.NotificationEvent;

public interface SmsSender {
    void send(NotificationEvent event);
}
