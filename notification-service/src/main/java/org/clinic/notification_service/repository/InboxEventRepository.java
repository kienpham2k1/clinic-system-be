package org.clinic.notification_service.repository;

import org.clinic.notification_service.model.sql.InboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface InboxEventRepository extends JpaRepository<InboxEventEntity, UUID> {

    Optional<InboxEventEntity> findByEventId(UUID eventId);

    @Modifying
    @Query("update InboxEventEntity e set e.status = :status, e.processedAt = :processedAt, e.retries = :retries where e.eventId = :eventId")
    int updateStatus(UUID eventId, String status, Instant processedAt, int retries);
}
