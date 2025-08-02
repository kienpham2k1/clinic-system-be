package org.clinic.notification_service.service.Impl.sender;

import lombok.AllArgsConstructor;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.service.sender.PushNotificationSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PushNotificationSenderImpl implements PushNotificationSender {
    public void send(NotificationEvent event) {

    }
}
