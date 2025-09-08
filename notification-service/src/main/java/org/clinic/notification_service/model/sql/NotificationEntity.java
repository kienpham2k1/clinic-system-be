package org.clinic.notification_service.model.sql;

import jakarta.persistence.*;
import lombok.*;
import org.clinic.common_service_web.audit.sql.BaseEntity;
import org.clinic.notification_service.constant.SqlDatabaseConstant;
import org.clinic.notification_service.enums.NotificationStatus;
import org.clinic.notification_service.enums.NotificationType;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = SqlDatabaseConstant.NOTIFICATION)
public class NotificationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = SqlDatabaseConstant.NOTIFICATION_ID)
    private UUID id;
    @Column(name = SqlDatabaseConstant.NOTIFICATION_RECIPIENT_ID)
    private UUID recipientId;
    @Column(name = SqlDatabaseConstant.NOTIFICATION_TYPE)
    private NotificationType type;
    @Column(name = SqlDatabaseConstant.NOTIFICATION__SUBJECT)
    private String subject;
    @Column(name = SqlDatabaseConstant.NOTIFICATION_MESSAGE)
    private String message;
    @Column(name = SqlDatabaseConstant.NOTIFICATION_STATUS)
    private NotificationStatus status;
    @Column(name = SqlDatabaseConstant.NOTIFICATION_METADATA)
    private String metadata;
}
