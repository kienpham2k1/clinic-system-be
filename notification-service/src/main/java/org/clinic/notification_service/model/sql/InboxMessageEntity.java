package org.clinic.notification_service.model.sql;

import jakarta.persistence.*;
import lombok.*;
import org.clinic.common_service_web.audit.sql.BaseEntity;
import org.clinic.notification_service.constant.SqlDatabaseConstant;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = SqlDatabaseConstant.INBOX_MESSAGE)
public class InboxMessageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = SqlDatabaseConstant.INBOX_MESSAGE_ID)
    private UUID id;

    @Column(name = SqlDatabaseConstant.INBOX_MESSAGE_MESSAGE_ID)
    private UUID messageId;

    @Column(name = SqlDatabaseConstant.INBOX_MESSAGE_PAYLOAD)
    @Lob
    private String payload;
}
