package org.clinic.notification_service.scheduled;

import lombok.AllArgsConstructor;
import org.clinic.notification_service.enums.OutboxStatus;
import org.clinic.notification_service.kafka.KafkaProducerService;
import org.clinic.notification_service.model.sql.OutboxMessageEntity;
import org.clinic.notification_service.repository.OutboxMessageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class KafkaScheduled {
    private final OutboxMessageRepository outboxMessageRepository;
    private final KafkaProducerService kafkaProducerService;

    @Scheduled(fixedDelay = 5000)
    public void sendOutboxMessages() {
        List<OutboxMessageEntity> messages = outboxMessageRepository.findByStatus(OutboxStatus.PENDING);
        for (OutboxMessageEntity msg : messages) {
            try {
                kafkaProducerService.sendNotification(msg);
                msg.setStatus(OutboxStatus.SENT);
            } catch (Exception e) {
                msg.setStatus(OutboxStatus.FAILED);
            }
            outboxMessageRepository.save(msg);
        }
    }
}
