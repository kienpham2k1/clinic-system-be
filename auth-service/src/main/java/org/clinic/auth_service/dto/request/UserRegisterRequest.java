package org.clinic.auth_service.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterRequest {
    private String username;
    private String password;
}
