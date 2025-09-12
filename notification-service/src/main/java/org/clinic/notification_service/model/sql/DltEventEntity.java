package org.clinic.notification_service.model.sql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

// DltEvent.java
@Entity
@Table(name = "dlt_event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DltEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String originalTopic;
    private Integer originalPartition;
    private Long originalOffset;
    private String key;
    private String payload;
    private String exceptionClass;
    @Column(length = 2000)
    private String exceptionMessage;
    private String stacktrace;
    private Instant receivedAt = Instant.now();
    private boolean replayed = false;
}
