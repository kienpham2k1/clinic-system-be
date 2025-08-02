package org.clinic.notification_service.dto.request;

import lombok.*;
import org.clinic.notification_service.enums.NotificationStatus;
import org.clinic.notification_service.enums.NotificationType;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest implements Serializable {
    private UUID recipientId;
    private NotificationType type;
    private String subject;
    private String message;
    private Map<String, String> metadata;
}
