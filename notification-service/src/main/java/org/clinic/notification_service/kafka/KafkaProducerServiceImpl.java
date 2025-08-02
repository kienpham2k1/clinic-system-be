package org.clinic.notification_service.kafka;

import lombok.RequiredArgsConstructor;
import org.clinic.notification_service.model.NotificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public void sendNotification(NotificationEvent event) {
        kafkaTemplate.send("notification-topic", event);
    }
}