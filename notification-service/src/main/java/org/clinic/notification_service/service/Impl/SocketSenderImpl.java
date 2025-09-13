package org.clinic.notification_service.service.Impl;

import lombok.AllArgsConstructor;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.service.SocketSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SocketSenderImpl implements SocketSender {
    public void send(NotificationEvent event) {
throw new UnsupportedOperationException("Not supported yet.");
    }
}
