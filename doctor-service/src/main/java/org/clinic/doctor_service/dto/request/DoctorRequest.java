package org.clinic.doctor_service.dto.request;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorRequest {
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String phone;
    private String address1;
    private String address2;
    private LocalDate birthDate;
    private String country;
    private String city;
    private String state;
    private String zip;
    private String profileImage;
}
