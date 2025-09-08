package org.clinic.notification_service.model.sql;

import jakarta.persistence.*;
import lombok.*;
import org.clinic.notification_service.enums.AggregateType;
import org.clinic.notification_service.enums.OutboxStatus;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.clinic.notification_service.constant.SqlDatabaseConstant.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = OUTBOX_EVENT)
public class OutboxEventEntity {
    @Id
    @Column(name = OUTBOX_EVENT_ID)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = OUTBOX_EVENT_AGGREGATE_ID)
    private UUID aggregateId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = OUTBOX_EVENT_AGGREGATE_TYPE, nullable = false)
    private AggregateType aggregateType;

    @Column(name = OUTBOX_EVENT_TOPIC)
    private String topic;

    @Column(name = OUTBOX_EVENT_KEY)
    private String key;

    @Column(name = OUTBOX_EVENT_PAYLOAD, columnDefinition = "text")
    private String payload;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = OUTBOX_EVENT_STATUS)
    private OutboxStatus status;

    @Column(name = OUTBOX_EVENT_CREATED_AT)
    private LocalDateTime createdAt;

    @Column(name = OUTBOX_EVENT_SENT_AT)
    private LocalDateTime sendAt;
}
