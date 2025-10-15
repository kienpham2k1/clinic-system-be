package org.clinic.notification_service.service.Impl;

import lombok.AllArgsConstructor;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.service.SmsSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SmsSenderImpl implements SmsSender {
    @Override
    public void send(NotificationEvent notificationRequest) {
    throw new UnsupportedOperationException("Not supported yet.");
    }
}
