package org.clinic.notification_service.service.kafka;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.clinic.notification_service.model.sql.DltEventEntity;
import org.clinic.notification_service.repository.DltEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

@Service
@Slf4j
public class DltConsumerImpl implements DltConsumer {
    private final DltEventRepository repo;

    public DltConsumerImpl(DltEventRepository repo) {
        this.repo = repo;
    }

    @KafkaListener(topics = "${app.kafka.notification.topic.dlt:notification.dlt}", groupId = "${app.kafka.notification.group.dlt}:notification-groups")
    @Transactional
    public void onDlt(ConsumerRecord<String, String> record, Acknowledgment ack) {
        log.info("Received Dlt event: " + record.value());
        try {
            DltEventEntity e = new DltEventEntity();
            e.setOriginalTopic(getHeader(record, KafkaHeaders.DLT_ORIGINAL_TOPIC));
            e.setOriginalPartition(getIntHeader(record, KafkaHeaders.DLT_ORIGINAL_PARTITION));
            e.setOriginalOffset(getLongHeader(record, KafkaHeaders.DLT_ORIGINAL_OFFSET));
            e.setExceptionClass(getHeader(record, KafkaHeaders.DLT_EXCEPTION_FQCN));
            e.setExceptionMessage(getHeader(record, KafkaHeaders.DLT_EXCEPTION_MESSAGE));
            e.setStacktrace(getHeader(record, KafkaHeaders.DLT_EXCEPTION_STACKTRACE));
            e.setKey(record.key());
            e.setPayload(record.value());

            repo.save(e);
            ack.acknowledge();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private String getHeader(ConsumerRecord<?, ?> r, String headerKey) {
        var it = r.headers().headers(headerKey).iterator();
        return it.hasNext() ? new String(it.next().value()) : null;
    }

    private Integer getIntHeader(ConsumerRecord<?, ?> r, String headerKey) {
        Header header = r.headers().lastHeader(headerKey);
        return (header != null) ? ByteBuffer.wrap(header.value()).getInt() : null;
    }

    private Long getLongHeader(ConsumerRecord<?, ?> r, String headerKey) {
        Header header = r.headers().lastHeader(headerKey);
        return (header != null) ? ByteBuffer.wrap(header.value()).getLong() : null;
    }
}
