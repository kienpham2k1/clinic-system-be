package org.clinic.commonserviceweb.security.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.clinic.commonserviceweb.exception.AccessDeniedException;
import org.clinic.commonserviceweb.exception.NotFoundException;
import org.clinic.commonserviceweb.security.dto.UserContext;
import org.clinic.commonserviceweb.security.anotation.RequirePermission;
import org.clinic.commonserviceweb.security.enums.Permission;
import org.clinic.commonserviceweb.security.service.UserContextProvider;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    private final UserContextProvider userContextProvider;

    @Before("@annotation(requirePermission)")
    public void checkPermission(JoinPoint joinPoint, RequirePermission requirePermission) {
                System.out.println(">>> AOP triggered for permission check");
        UserContext userContext = userContextProvider.getContext();
        for (Permission permission : requirePermission.value()) {
            if (!userContext.getPermission().contains(permission)) {
                throw new AccessDeniedException("Permission denied: " + permission.name());
            }
        }
    }
}