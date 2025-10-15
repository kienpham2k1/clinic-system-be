package org.clinic.auth_service.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateRequest {
    private String username;
    private String password;
}
