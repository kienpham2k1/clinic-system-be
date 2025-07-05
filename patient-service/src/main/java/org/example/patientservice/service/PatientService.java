package org.example.patientservice.service;

import org.example.patientservice.dto.request.PatientRequestDto;
import org.example.patientservice.dto.response.PatientResponseDto;
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
