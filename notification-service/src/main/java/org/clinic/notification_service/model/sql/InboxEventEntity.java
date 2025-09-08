package org.clinic.notification_service.model.sql;

import jakarta.persistence.*;
import lombok.*;
import org.clinic.notification_service.enums.InboxStatus;

import java.time.Instant;
import java.util.UUID;

import static org.clinic.notification_service.constant.SqlDatabaseConstant.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = INBOX_EVENT)
public class InboxEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = INBOX_EVENT_ID)
    private UUID eventId;

    @Column(name = INBOX_EVENT_AGGREGATE_ID, unique = true, nullable = false)
    private UUID aggregateId;

    @Column(name = INBOX_EVENT_PAYLOAD, columnDefinition = "text")
    private String payload;

    @Column(name = INBOX_EVENT_RECEIVED_AT)
    private Instant receivedAt = Instant.now();

    @Column(name = INBOX_EVENT_PROCESSED_AT)
    private Instant processedAt;

    @Column(name = INBOX_EVENT_STATUS, nullable = false)
    private InboxStatus status;

    @Column(name = INBOX_EVENT_RETRIES, nullable = false)
    private int retries = 0;

    public InboxEventEntity(UUID aggregateId, String payload, InboxStatus status) {
        this.aggregateId = aggregateId;
        this.payload = payload;
        this.status = status;
    }
}
