package org.clinic.notification_service.service.Impl;

import lombok.AllArgsConstructor;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.service.EmailSender;
import org.clinic.notification_service.service.NotificationDirectService;
import org.clinic.notification_service.service.SmsSender;
import org.clinic.notification_service.service.SocketSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationDirectServiceImpl implements NotificationDirectService {
    private final EmailSender emailSender;
    private final SmsSender smsSender;
    private final SocketSender pushSender;

    @Override
    public void directSend(NotificationEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
//        switch (event.getType()) {
//            case EMAIL -> emailSender.send(event);
//            case SMS -> smsSender.send(event);
//            case PUSH -> pushSender.send(event);
//            default -> throw new IllegalArgumentException("Unknown type: " + event.getType());
//        }
    }
}
