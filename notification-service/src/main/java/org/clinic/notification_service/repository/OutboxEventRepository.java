package org.clinic.notification_service.repository;

import jakarta.persistence.LockModeType;
import org.clinic.notification_service.model.sql.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from OutboxEventEntity o where o.status = org.clinic.notification_service.enums.OutboxStatus.PENDING")
    List<OutboxEventEntity> findPending();
}
