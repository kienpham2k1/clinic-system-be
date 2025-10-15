package org.clinic.notification_service.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

public interface DltConsumer {
    void onDlt(ConsumerRecord<String, String> record, Acknowledgment ack);
}
