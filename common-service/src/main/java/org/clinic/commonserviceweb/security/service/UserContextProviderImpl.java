package org.clinic.commonserviceweb.security.service;

import org.clinic.commonserviceweb.security.config.UserContextHolder;
import org.clinic.commonserviceweb.security.dto.UserContext;
import org.clinic.commonserviceweb.security.enums.Permission;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Component
public class UserContextProviderImpl implements UserContextProvider {

    public Optional<UserContext> getCurrentUser() {
        return Optional.ofNullable(UserContextHolder.getContext());
    }

    public UserContext getContext() {
        return getCurrentUser().orElse(null);
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

    @Override
    public Set<Permission> getPermission() {
        return getCurrentUser().map(UserContext::getPermission).orElse(Collections.emptySet());
    }

}