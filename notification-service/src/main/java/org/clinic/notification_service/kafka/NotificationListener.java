package org.clinic.notification_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.service.NotificationActService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {
    private final NotificationActService notificationActService;

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void listen(NotificationEvent event) {
        log.info("Received notification event: {}", event);
        notificationActService.act(event);
    }
}
