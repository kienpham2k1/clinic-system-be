package org.clinic.notification_service.model.sql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

import static org.clinic.notification_service.constant.SqlDatabaseConstant.*;

// DltEvent.java
@Entity
@Table(name = DLT_EVENT)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DltEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = DLT_EVENT_ID)
    private UUID id;

    @Column(name = DLT_EVENT_ORIGINAL_TOPIC)
    private String originalTopic;

    @Column(name = DLT_EVENT_ORIGINAL_PARTITION)
    private Integer originalPartition;

    @Column(name = DLT_EVENT_ORIGINAL_OFFSET)
    private Long originalOffset;

    @Column(name = DLT_EVENT_KEY)
    private String key;

    @Column(name = DLT_EVENT_PAYLOAD)
    private String payload;

    @Column(name = DLT_EVENT_EXCEPTION_CLASS)
    private String exceptionClass;

    @Column(name = DLT_EVENT_EXCEPTION_MESSAGE, columnDefinition = "text")
    private String exceptionMessage;

    @Column(name = DLT_EVENT_STACKTRACE, columnDefinition = "text")
    private String stacktrace;

    @Column(name = DLT_EVENT_RECEIVED_AT)
    private Instant receivedAt = Instant.now();

    @Column(name = DLT_EVENT_REPLAYED)
    private boolean replayed = false;
}
