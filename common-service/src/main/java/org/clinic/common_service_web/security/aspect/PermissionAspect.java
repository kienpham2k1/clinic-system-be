package org.clinic.common_service_web.security.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.clinic.common_security.security.enums.Permission;
import org.clinic.common_service_web.exception.AccessDeniedException;
import org.clinic.common_service_web.security.anotation.RequirePermission;
import org.clinic.common_service_web.security.dto.UserContext;
import org.clinic.common_service_web.security.service.UserContextProvider;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    private final UserContextProvider userContextProvider;

    @Before("@annotation(requirePermission)")
    public void checkPermission(JoinPoint joinPoint, RequirePermission requirePermission) {
        UserContext userContext = userContextProvider.getContext();
        for (Permission permission : requirePermission.value()) {
            if (!userContext.getPermission().contains(permission)) {
                throw new AccessDeniedException("Permission denied: " + permission.name());
            }
        }
    }
}