package org.example.patientservice.config.audit;

import jakarta.servlet.http.HttpServletRequest;
import org.example.commonservice.commonAudit.entity.audit.UserContextProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class HeaderUserContextProvider implements UserContextProvider {

    @Override
    public String getCurrentUsername() {
        try {
            ServletRequestAttributes attrs =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs == null) return null;

            HttpServletRequest request = attrs.getRequest();
            return request.getHeader("X-USER-ID");
        } catch (Exception e) {
            return null;
        }
    }
}