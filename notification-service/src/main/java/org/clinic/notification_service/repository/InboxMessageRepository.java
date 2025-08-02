package org.clinic.notification_service.repository;

import org.clinic.notification_service.model.sql.InboxMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface InboxMessageRepository extends JpaRepository<InboxMessageEntity, UUID> {
    @Query("select (count(i) > 0) from InboxMessageEntity i where i.messageId = ?1")
    boolean existsByMessageId(UUID key);
}
