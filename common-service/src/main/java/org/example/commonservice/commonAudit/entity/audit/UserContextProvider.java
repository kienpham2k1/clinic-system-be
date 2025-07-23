package org.example.commonservice.commonAudit.entity.audit;

import org.springframework.stereotype.Service;

@Service
public interface UserContextProvider {
    String getCurrentUsername();
}
