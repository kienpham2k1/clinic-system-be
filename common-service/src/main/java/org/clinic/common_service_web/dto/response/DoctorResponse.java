package org.clinic.common_service_web.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorResponse {
    private UUID id;
    private String firstName;
    private String lastName;
}
