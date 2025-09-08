package org.clinic.notification_service.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.clinic.notification_service.model.NotificationEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service

public interface NotificationConsumer {
    void consume(NotificationEvent event);

    void consume(ConsumerRecord<String, String> record, Acknowledgment ack) throws JsonProcessingException;
}
