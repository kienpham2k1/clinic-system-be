package org.clinic.auth_service.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    public String username;
    public String password;
}
