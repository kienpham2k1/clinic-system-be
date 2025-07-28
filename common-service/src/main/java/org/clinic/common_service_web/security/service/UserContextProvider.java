package org.clinic.common_service_web.security.service;

import org.clinic.common_security.security.enums.Permission;
import org.clinic.common_security.security.enums.Role;
import org.clinic.common_service_web.security.dto.UserContext;

import java.util.Optional;
import java.util.Set;

public interface UserContextProvider {

    Optional<UserContext> getCurrentUser();

    UserContext getContext();

    String getUserId();

    String getUsername();

    Set<Role> getRole();

    Set<Permission> getPermission();
}