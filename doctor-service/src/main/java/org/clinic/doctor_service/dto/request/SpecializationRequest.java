package org.clinic.doctor_service.dto.request;

import lombok.*;
import org.clinic.doctor_service.enums.DepartmentStatus;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpecializationRequest {
    private String name;
    private Long noOfDoctor;
    private DepartmentStatus status;
}
