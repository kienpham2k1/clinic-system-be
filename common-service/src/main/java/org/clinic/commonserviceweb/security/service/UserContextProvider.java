package org.clinic.commonserviceweb.security.service;

import org.clinic.commonserviceweb.security.enums.Permission;
import org.clinic.commonserviceweb.security.dto.UserContext;

import java.util.Optional;
import java.util.Set;

public interface UserContextProvider {

    Optional<UserContext> getCurrentUser();

    UserContext getContext();

    String getUserId();

    String getUsername();

    String getRole();

    Set<Permission> getPermission();
}