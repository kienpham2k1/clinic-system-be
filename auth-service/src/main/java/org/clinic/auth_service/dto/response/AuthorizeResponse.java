package org.clinic.auth_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorizeResponse {
    private RoleResponse role;
}
