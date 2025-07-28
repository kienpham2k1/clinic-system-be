package org.clinic.patient_service.service;

import org.clinic.patient_service.dto.request.PatientRequestDto;
import org.clinic.patient_service.dto.response.PatientResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PatientService {
    Page<PatientResponseDto> getPatientsPage(Pageable pageable);

    PatientResponseDto getPatientById(UUID patientId);

    PatientResponseDto insertPatient(PatientRequestDto patientRequestDto);

    PatientResponseDto updatePatient(UUID patientId, PatientRequestDto patientRequestDto);

    PatientResponseDto deletePatient(UUID patientId);
}
