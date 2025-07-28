package org.clinic.authservice.dto.response;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    List<AuthorizeResponse> authorizes;
    private UUID id;
    private String username;
    private String password;
    private String email;
}
