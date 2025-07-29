package org.clinic.doctor_service.dto.response;

import lombok.*;
import org.clinic.doctor_service.enums.DepartmentStatus;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentResponse {
    private UUID id;
    private String name;
    private Long noOfDoctor;
    private DepartmentStatus status;
}
