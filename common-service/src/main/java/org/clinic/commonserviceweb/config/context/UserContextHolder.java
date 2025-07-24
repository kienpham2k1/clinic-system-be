package org.clinic.commonserviceweb.config.context;

import org.clinic.commonserviceweb.dto.UserContext;

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