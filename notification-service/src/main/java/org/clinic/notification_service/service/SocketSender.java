package org.clinic.notification_service.service;

import org.clinic.notification_service.model.NotificationEvent;

public interface SocketSender {
    void send(NotificationEvent event);
}
