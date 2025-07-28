package org.clinic.authservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorizeResponse {
    private RoleResponse role;
}
