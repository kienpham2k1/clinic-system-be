package org.clinic.notification_service.repository;

import org.clinic.notification_service.enums.OutboxStatus;
import org.clinic.notification_service.model.sql.OutboxMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxMessageRepository extends JpaRepository<OutboxMessageEntity, UUID> {
    @Query("select o from OutboxMessageEntity o where o.status = ?1")
    List<OutboxMessageEntity> findByStatus(OutboxStatus outboxStatus);
}
