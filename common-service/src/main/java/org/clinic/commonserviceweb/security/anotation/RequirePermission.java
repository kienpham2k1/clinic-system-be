package org.clinic.commonserviceweb.security.anotation;

import org.clinic.commonserviceweb.security.enums.Permission;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {
    Permission[] value();
}