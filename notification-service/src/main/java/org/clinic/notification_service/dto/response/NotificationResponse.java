package org.clinic.notification_service.dto.response;

import lombok.*;
import org.clinic.notification_service.enums.NotificationStatus;
import org.clinic.notification_service.enums.NotificationType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private UUID id;
    private UUID recipientId;
    private NotificationType type;
    private String subject;
    private String message;
    private NotificationStatus status;
    private String metadata;
}
