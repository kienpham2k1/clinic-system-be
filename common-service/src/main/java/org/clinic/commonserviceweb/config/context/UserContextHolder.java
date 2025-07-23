package org.clinic.commonserviceweb.config.context;

import org.clinic.commonserviceweb.dto.UserContext;

public class UserContextHolder {
    private static final ThreadLocal<UserContext> contextHolder = new ThreadLocal<>();

    public static UserContext getContext() {
        return contextHolder.get();
    }

    public static void setContext(UserContext context) {
        contextHolder.set(context);
    }

    public static void clear() {
        contextHolder.remove();
    }
}