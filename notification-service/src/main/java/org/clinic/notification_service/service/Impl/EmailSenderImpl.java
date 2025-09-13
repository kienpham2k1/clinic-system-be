package org.clinic.notification_service.service.Impl;

import lombok.AllArgsConstructor;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.service.EmailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderImpl implements EmailSender {
    @Override
    public void send(NotificationEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
