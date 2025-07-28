package org.clinic.authservice.dto.response;

import lombok.Builder;
import lombok.Data;
import org.clinic.authservice.model.sql.RoleEntity;

@Data
@Builder
public class AuthorizeResponse {
    private RoleResponse role;
}
