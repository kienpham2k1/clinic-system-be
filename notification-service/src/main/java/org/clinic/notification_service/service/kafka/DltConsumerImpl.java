package org.clinic.notification_service.service.kafka;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.clinic.notification_service.model.sql.DltEventEntity;
import org.clinic.notification_service.repository.DltEventRepository;
import org.clinic.notification_service.utils.KafkaHeaderUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DltConsumerImpl implements DltConsumer {
    private final DltEventRepository repo;

    public DltConsumerImpl(DltEventRepository repo) {
        this.repo = repo;
    }

    @KafkaListener(topics = "${app.kafka.notification.topic.dlt:notification.dlt}", groupId = "${app.kafka.notification.group.dlt:notification-groups.dlt}")
    @Transactional
    public void onDlt(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("Received Dlt event: " + record.value());
        try {
            DltEventEntity e = new DltEventEntity();
            e.setOriginalTopic(KafkaHeaderUtils.getHeader(record, KafkaHeaders.DLT_ORIGINAL_TOPIC));
            e.setOriginalPartition(KafkaHeaderUtils.getIntHeader(record, KafkaHeaders.DLT_ORIGINAL_PARTITION));
            e.setOriginalOffset(KafkaHeaderUtils.getLongHeader(record, KafkaHeaders.DLT_ORIGINAL_OFFSET));
            e.setExceptionClass(KafkaHeaderUtils.getHeader(record, KafkaHeaders.DLT_EXCEPTION_FQCN));
            e.setExceptionMessage(KafkaHeaderUtils.getHeader(record, KafkaHeaders.DLT_EXCEPTION_MESSAGE));
            e.setStacktrace(KafkaHeaderUtils.getHeader(record, KafkaHeaders.DLT_EXCEPTION_STACKTRACE));
            e.setKey(record.key());
            e.setPayload(record.value());

            repo.save(e);
            ack.acknowledge();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
