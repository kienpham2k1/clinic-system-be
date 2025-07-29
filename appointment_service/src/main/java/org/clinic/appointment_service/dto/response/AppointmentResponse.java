package org.clinic.appointment_service.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.clinic.appointment_service.constant.SqlDatabaseConstant;
import org.clinic.appointment_service.enums.Mode;
import org.clinic.appointment_service.enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentResponse {
    private UUID id;
    private UUID doctorId;
    private UUID patientId;
    private UUID departmentId;
    private Mode mode;
    private LocalDate date;
    private LocalTime time;
    private Status status;
}
