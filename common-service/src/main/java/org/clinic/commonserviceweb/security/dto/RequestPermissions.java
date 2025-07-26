package org.clinic.commonserviceweb.security.dto;

import org.clinic.commonserviceweb.security.enums.Permission;
import org.clinic.commonserviceweb.security.enums.Role;
import org.springframework.http.HttpMethod;

import java.util.Map;
import java.util.Set;

public class RequestPermissions {
    private final Set<Role> roles;
    private final Map<HttpMethod, Set<Permission>> authorities;

    public RequestPermissions(Set<Role> roles,
                              Map<HttpMethod, Set<Permission>> authorities) {
        this.roles = roles;
        this.authorities = authorities;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Map<HttpMethod, Set<Permission>> getAuthorities() {
        return authorities;
    }
}
