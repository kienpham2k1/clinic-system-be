package org.clinic.common_service_web.security.service;

import org.clinic.common_security.security.enums.Permission;
import org.clinic.common_security.security.enums.Role;
import org.clinic.common_service_web.security.config.UserContextHolder;
import org.clinic.common_service_web.security.dto.UserContext;
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
        return getCurrentUser().map(UserContext::getUserId).orElse(null);
    }

    public String getUsername() {
        return getCurrentUser().map(UserContext::getUsername).orElse("unknown");
    }

    public Set<Role> getRole() {
        return getCurrentUser().map(UserContext::getRole).orElse(Collections.emptySet());
    }

    @Override
    public Set<Permission> getPermission() {
        return getCurrentUser().map(UserContext::getPermission).orElse(Collections.emptySet());
    }

}