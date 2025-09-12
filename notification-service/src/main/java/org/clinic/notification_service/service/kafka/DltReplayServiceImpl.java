package org.clinic.notification_service.service.kafka;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.clinic.notification_service.model.sql.DltEventEntity;
import org.clinic.notification_service.repository.DltEventRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DltReplayServiceImpl implements DltReplayService {
    private final DltEventRepository dltEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Transactional
    public void replay(UUID dltId) {
        DltEventEntity dltEvent = dltEventRepository.findById(dltId).orElseThrow(() -> new IllegalArgumentException("DltEvent not found"));
        if (dltEvent.isReplayed()) {
            return;
        }
        if (dltEvent.getOriginalPartition() != null) {
            //send back to kafka topic
            kafkaTemplate.send(dltEvent.getOriginalTopic(), dltEvent.getOriginalPartition(), dltEvent.getKey(), dltEvent.getPayload());
        } else {
            kafkaTemplate.send(dltEvent.getOriginalTopic(), dltEvent.getKey(), dltEvent.getPayload());
        }
        //update status dlt event
        dltEvent.setReplayed(true);
        dltEventRepository.save(dltEvent);
    }
}
