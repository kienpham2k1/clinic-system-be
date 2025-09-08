package org.clinic.notification_service.service;

import org.clinic.notification_service.enums.NotificationStatus;
import org.clinic.notification_service.model.NotificationEvent;

import java.time.Instant;
import java.util.UUID;

public interface NotificationDirectService {
    void directSend(NotificationEvent event);
}
