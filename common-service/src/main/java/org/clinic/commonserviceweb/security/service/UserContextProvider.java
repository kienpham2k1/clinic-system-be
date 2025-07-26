package org.clinic.commonserviceweb.security.service;

import org.clinic.commonserviceweb.security.dto.UserContext;
import org.clinic.commonserviceweb.security.enums.Permission;
import org.clinic.commonserviceweb.security.enums.Role;

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