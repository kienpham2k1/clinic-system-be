package org.clinic.notification_service.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.model.sql.OutboxEventEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationPublisherImpl implements NotificationPublisher {
    private final KafkaTemplate<String, NotificationEvent> kafkaTemplateNotification;
    private final KafkaTemplate<String, String> kafkaTemplateOutbox;

    @Value("${app.kafka.notification.topic:notification}")
    private String NOTIFICATION_TOPIC;

    @Override
    public void publishNotificationEvent(NotificationEvent event) {
        kafkaTemplateNotification.send(NOTIFICATION_TOPIC, event);
    }

    @Override
    public void publishNotificationEvent(OutboxEventEntity event, UUID aggregateId) {
        ProducerRecord<String, String> record = new ProducerRecord<>(NOTIFICATION_TOPIC, aggregateId.toString(), event.getPayload());
        record.headers().add(new RecordHeader("aggregateId", aggregateId.toString().getBytes(StandardCharsets.UTF_8)));
//        record.headers().add(new RecordHeader("eventType", eventType.getBytes(StandardCharsets.UTF_8)));
        kafkaTemplateOutbox.send(record)
                .thenAccept(rs -> {
                    log.info("✅ Sent message | key= {} | value= {} | partition= {}", aggregateId, event.getPayload(), rs.getRecordMetadata().partition());
                })
                .exceptionally(ex -> {
                    log.error("❌ Failed to send message: {}", ex.getMessage());
                    return null;
                });
    }
}