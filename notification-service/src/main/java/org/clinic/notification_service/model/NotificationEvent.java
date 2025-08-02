package org.clinic.notification_service.model;

import lombok.*;
import org.clinic.notification_service.enums.NotificationStatus;
import org.clinic.notification_service.enums.NotificationType;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEvent {
    private UUID id;
    private UUID recipientId;
    private NotificationType type;
    private String subject;
    private String message;
    private NotificationStatus status;
    private Map<String, String> metadata;
}
