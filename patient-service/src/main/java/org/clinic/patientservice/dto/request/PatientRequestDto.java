package org.clinic.patientservice.dto.request;

import lombok.*;
import org.clinic.common_service_web.validation.annotation.RequiredField;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PatientRequestDto {
    @RequiredField
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zip;
}
