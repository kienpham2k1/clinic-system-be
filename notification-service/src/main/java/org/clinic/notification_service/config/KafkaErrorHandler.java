package org.clinic.notification_service.config;

import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.ExponentialBackOff;

@Configuration
public class KafkaErrorHandler {
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<Object, Object> template) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(template,
                (record, ex) -> {
                    // route đến <topic>.DLT giữ nguyên partition
                    return new TopicPartition(record.topic() + ".dlt", record.partition());
                });

        ExponentialBackOff backoff = new ExponentialBackOff(500L, 2.0); // 0.5s, 1s, 2s, 4s...
        backoff.setMaxElapsedTime(15_000L); // tối đa 15s

        return new DefaultErrorHandler(recoverer, backoff);
    }
}
