package org.clinic.notification_service.service;

import org.clinic.notification_service.dto.request.NotificationRequest;
import org.clinic.notification_service.dto.response.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    Page<NotificationResponse> getNotificationByPage(Pageable pageable);

    NotificationResponse getNotificationById(UUID notificationId);

    NotificationResponse insertNotification(NotificationRequest notificationRequest);

    NotificationResponse updateNotification(UUID notificationId, NotificationRequest notificationRequest);

    NotificationResponse deleteNotification(UUID notificationId);

    List<NotificationResponse> getNotificationsList(List<UUID> notificationIds);

    NotificationResponse markAsRead(UUID notificationId);
}
