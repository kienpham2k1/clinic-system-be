package org.clinic.common_service_web.security.config;

import org.clinic.common_service_web.security.dto.UserContext;

public class UserContextHolder {
    private static final ThreadLocal<UserContext> userContextHolder = new ThreadLocal<>();

    public static UserContext getContext() {
        return userContextHolder.get();
    }

    public static void setContext(UserContext context) {
        userContextHolder.set(context);
    }

    public static void clear() {
        userContextHolder.remove();
    }
}