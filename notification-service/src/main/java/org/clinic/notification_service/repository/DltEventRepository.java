package org.clinic.notification_service.repository;

import org.clinic.notification_service.model.sql.DltEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DltEventRepository extends JpaRepository<DltEventEntity, UUID> {
}
