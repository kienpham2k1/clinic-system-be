package org.clinic.auth_service.dto.response;

import lombok.Builder;
import lombok.Data;
import org.clinic.common_security.security.enums.Role;

import java.util.UUID;

@Data
@Builder
public class RoleResponse {
    private UUID id;
    private Role name;
}
