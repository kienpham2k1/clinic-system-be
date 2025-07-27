package org.clinic.authservice.dto.response;

import lombok.*;
import org.clinic.commonserviceweb.wrapper.dto.BaseResponse;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponse extends BaseResponse {
    private UUID id;
    private String username;
    private String email;
}
