package org.clinic.appointment_service.dto.request;

import lombok.*;
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
public class AppointmentRequest {
    private UUID doctorId;
    private UUID patientId;
    private UUID departmentId;
    private Mode mode;
    private LocalDate date;
    private LocalTime time;
    private Status status;
}
