package org.clinic.authservice.dto.request;

import lombok.*;
import org.clinic.common_security.security.enums.Role;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleRequest {
    private Role name;
}
