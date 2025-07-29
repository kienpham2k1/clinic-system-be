package org.clinic.doctor_service.dto.request;

import lombok.*;
import org.clinic.doctor_service.enums.DepartmentStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentRequest {
    private String name;
    private Long noOfDoctor;
    private DepartmentStatus status;
}
