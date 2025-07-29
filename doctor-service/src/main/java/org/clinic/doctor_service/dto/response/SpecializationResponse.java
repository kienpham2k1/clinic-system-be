package org.clinic.doctor_service.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.clinic.doctor_service.constant.SqlDatabaseConstant;
import org.clinic.doctor_service.enums.SpecializationStatus;

import java.time.LocalDate;
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
