package org.clinic.notification_service.service.Impl;

import lombok.AllArgsConstructor;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.service.NotificationActService;
import org.clinic.notification_service.service.sender.EmailSender;
import org.clinic.notification_service.service.sender.PushNotificationSender;
import org.clinic.notification_service.service.sender.SmsSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationActServiceImpl implements NotificationActService {
    private final EmailSender emailSender;
    private final SmsSender smsSender;
    private final PushNotificationSender pushSender;

    @Override
    public void act(NotificationEvent event) {
        switch (event.getType()) {
            case EMAIL -> emailSender.send(event);
            case SMS -> smsSender.send(event);
            case PUSH -> pushSender.send(event);
            default -> throw new IllegalArgumentException("Unknown type: " + event.getType());
        }
    }
}
