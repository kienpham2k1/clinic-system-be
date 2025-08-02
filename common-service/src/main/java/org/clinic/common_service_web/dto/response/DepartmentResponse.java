package org.clinic.common_service_web.dto.response;

import lombok.*;
import org.clinic.common_service_web.enums.DepartmentStatus;

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
