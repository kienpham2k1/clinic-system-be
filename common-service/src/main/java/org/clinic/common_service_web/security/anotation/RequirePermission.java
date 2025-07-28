package org.clinic.common_service_web.security.anotation;

import org.clinic.common_security.security.enums.Permission;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {
    Permission[] value();
}