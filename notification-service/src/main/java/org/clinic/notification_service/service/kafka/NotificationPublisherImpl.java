package org.clinic.notification_service.service.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.model.sql.OutboxEventEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationPublisherImpl implements NotificationPublisher {
    private final KafkaTemplate<String, NotificationEvent> kafkaTemplateNotification;
    private final KafkaTemplate<String, String> kafkaTemplateOutbox;

    @Value("${kafka.topic.notification.name:notification}")
    private String NOTIFICATION_TOPIC;

    @Override
    public void publishNotificationEvent(NotificationEvent event) {
        kafkaTemplateNotification.send(NOTIFICATION_TOPIC, event);
    }

    @Override
    public void publishNotificationEvent(OutboxEventEntity event, UUID aggregateId) {
        ProducerRecord<String, String> record = new ProducerRecord<>(NOTIFICATION_TOPIC,  event.getPayload());
        record.headers().add(new RecordHeader("aggregateId", aggregateId.toString().getBytes(StandardCharsets.UTF_8)));
//        record.headers().add(new RecordHeader("eventType", eventType.getBytes(StandardCharsets.UTF_8)));
        kafkaTemplateOutbox.send(record);
    }
}