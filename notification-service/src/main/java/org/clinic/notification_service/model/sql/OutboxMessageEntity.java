package org.clinic.notification_service.model.sql;

import jakarta.persistence.*;
import lombok.*;
import org.clinic.common_service_web.audit.sql.BaseEntity;
import org.clinic.notification_service.constant.SqlDatabaseConstant;
import org.clinic.notification_service.enums.EventType;
import org.clinic.notification_service.enums.OutboxStatus;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = SqlDatabaseConstant.OUTBOX_MESSAGE)
public class OutboxMessageEntity extends BaseEntity {
    @Id
    @Column(name = SqlDatabaseConstant.OUTBOX_MESSAGE_ID)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = SqlDatabaseConstant.OUTBOX_MESSAGE_KAFKA_MESSAGE_ID)
    private UUID kafkaId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = SqlDatabaseConstant.OUTBOX_MESSAGE_EVENT_TYPE)
    private EventType eventType;

    @Column(name = SqlDatabaseConstant.OUTBOX_MESSAGE_PAYLOAD)
    private String payload;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = SqlDatabaseConstant.OUTBOX_MESSAGE_STATUS)
    private OutboxStatus status;
}
