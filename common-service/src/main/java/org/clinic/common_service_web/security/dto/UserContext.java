package org.clinic.common_service_web.security.dto;

import org.clinic.common_security.security.enums.Permission;
import org.clinic.common_security.security.enums.Role;

import java.util.Set;

public class UserContext {
    private final String userId;
    private final String username;
    private final Set<Role> role;
    private final Set<Permission> permission;

    public UserContext(String userId, String username, Set<Role> role, Set<Permission> permission) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.permission = permission;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Set<Role> getRole() {
        return role;
    }

    public Set<Permission> getPermission() {
        return permission;
    }
}