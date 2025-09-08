package org.clinic.notification_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.clinic.notification_service.dto.request.NotificationRequest;
import org.clinic.notification_service.dto.response.NotificationResponse;
import org.clinic.notification_service.enums.NotificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface NotificationService {
    Page<NotificationResponse> getNotificationByPage(Pageable pageable);

    NotificationResponse getNotificationById(UUID notificationId);

    NotificationResponse insertNotification(NotificationRequest notificationRequest) throws JsonProcessingException;

    NotificationResponse updateNotification(UUID notificationId, NotificationRequest notificationRequest);

    NotificationResponse deleteNotification(UUID notificationId);

    List<NotificationResponse> getNotificationsList(List<UUID> notificationIds);

    NotificationResponse markAsRead(UUID notificationId);

    void updateStatus(UUID eventId, NotificationStatus notificationStatus, Instant now, int i);
}
