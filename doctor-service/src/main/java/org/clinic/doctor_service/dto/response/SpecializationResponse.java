package org.clinic.doctor_service.dto.response;

import lombok.*;
import org.clinic.doctor_service.enums.SpecializationStatus;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpecializationResponse {
    private UUID id;
    private String name;
    private Long noOfDoctor;
    private SpecializationStatus status;
}
