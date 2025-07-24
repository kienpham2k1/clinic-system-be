package org.clinic.commonserviceweb.security.dto;

import org.clinic.commonserviceweb.security.enums.Permission;

import java.util.Set;

public class UserContext {
    private final String userId;
    private final String username;
    private final String role;
    private final Set<Permission> permission;

    public UserContext(String userId, String username, String role, Set<Permission> permission) {
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

    public String getRole() {
        return role;
    }

    public Set<Permission> getPermission() {
        return permission;
    }
}