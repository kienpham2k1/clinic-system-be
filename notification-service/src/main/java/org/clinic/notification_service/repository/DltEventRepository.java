package org.clinic.notification_service.repository;

import org.clinic.notification_service.model.sql.DltEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DltEventRepository extends JpaRepository<DltEventEntity, Long> {
}
