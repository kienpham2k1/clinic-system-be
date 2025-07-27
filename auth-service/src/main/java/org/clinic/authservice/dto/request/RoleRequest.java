package org.clinic.authservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleRequest {
    private String roleName;
}
