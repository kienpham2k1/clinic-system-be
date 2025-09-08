package org.clinic.notification_service.scheduled;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.clinic.notification_service.enums.OutboxStatus;
import org.clinic.notification_service.model.sql.OutboxEventEntity;
import org.clinic.notification_service.repository.OutboxEventRepository;
import org.clinic.notification_service.service.kafka.NotificationPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class OutboxPublisher  {
    private final OutboxEventRepository outboxEventRepository;
    private final NotificationPublisher notificationPublisher;

    @Scheduled(fixedDelayString = "${outbox.poll-ms:5000}")
    @Transactional
    public void publishPending() {
        List<OutboxEventEntity> events = outboxEventRepository.findPending();
        for (OutboxEventEntity e : events) {
            try {
                UUID aggregateId = e.getAggregateId();
                e.setStatus(OutboxStatus.SENT);
                e.setAggregateId(aggregateId);
                e.setSendAt(LocalDateTime.now());
                notificationPublisher.publishNotificationEvent(e, aggregateId);
            } catch (Exception ex) {
                e.setStatus(OutboxStatus.FAILED);
            }
            outboxEventRepository.save(e);
        }
    }
}
