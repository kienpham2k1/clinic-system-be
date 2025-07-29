package org.clinic.appointment_service.service;

import org.clinic.appointment_service.dto.response.AppointmentResponse;
import org.clinic.appointment_service.dto.request.AppointmentRequest;
import org.clinic.appointment_service.dto.response.AppointmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    Page<AppointmentResponse> getAppointmentByPage(Pageable pageable);

    List<AppointmentResponse> getAppointments();

    AppointmentResponse getAppointmentById(UUID appointmentId);

    AppointmentResponse insertAppointment(AppointmentRequest appointmentRequest);

    AppointmentResponse updateAppointment(UUID appointmentId, AppointmentRequest appointmentRequest);

    AppointmentResponse deleteAppointment(UUID appointmentId);

}
