package org.clinic.notification_service.service.kafka;

import java.util.UUID;

public interface DltReplayService {
    void replay(UUID dltId);
}
