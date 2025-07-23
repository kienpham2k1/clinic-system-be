package org.clinic.commonserviceweb.service;

import org.clinic.commonserviceweb.config.context.UserContextHolder;
import org.clinic.commonserviceweb.dto.UserContext;
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