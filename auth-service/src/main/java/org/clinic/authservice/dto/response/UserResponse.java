package org.clinic.authservice.dto.response;

import lombok.*;
import org.clinic.commonserviceweb.audit.sql.BaseEntity;

import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private UUID id;
    private String username;
    private String password;
    private String email;
    List<RoleResponse> roles;
}
