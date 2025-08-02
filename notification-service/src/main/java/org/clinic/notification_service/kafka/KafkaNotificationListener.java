package org.clinic.notification_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.clinic.notification_service.model.NotificationEvent;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service

public interface KafkaNotificationListener {
    void listen(NotificationEvent event);

    void listen(UUID messageId, String message) throws JsonProcessingException;
}
