package org.clinic.common_service_web.context;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserContextProviderImpl implements UserContextProvider {

    public Optional<UserContext> getCurrentUser() {
        return Optional.ofNullable(UserContextHolder.getContext());
    }

    public String getUserId() {
        return getCurrentUser().map(UserContext::getUserId).orElse("unknown");
    }

    public String getUsername() {
        return getCurrentUser().map(UserContext::getUsername).orElse("unknown");
    }

    public String getRole() {
        return getCurrentUser().map(UserContext::getRole).orElse("GUEST");
    }
}