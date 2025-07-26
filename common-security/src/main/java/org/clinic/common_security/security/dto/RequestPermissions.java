package org.clinic.common_security.security.dto;

import org.clinic.common_security.security.enums.Permission;
import org.clinic.common_security.security.enums.Role;
import org.springframework.http.HttpMethod;

import java.util.Map;
import java.util.Set;

public record RequestPermissions(Set<Role> roles, Map<HttpMethod, Set<Permission>> authorities) {
}
