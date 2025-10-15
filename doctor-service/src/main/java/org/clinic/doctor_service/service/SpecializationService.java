package org.clinic.doctor_service.service;

import org.clinic.doctor_service.dto.request.SpecializationRequest;
import org.clinic.doctor_service.dto.response.SpecializationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SpecializationService {
    Page<SpecializationResponse> getSpecializationByPage(Pageable pageable);

    List<SpecializationResponse> getSpecializations();

    SpecializationResponse getSpecializationById(UUID specializationId);

    SpecializationResponse insertSpecialization(SpecializationRequest specializationRequest);

    SpecializationResponse updateSpecialization(UUID specializationId, SpecializationRequest specializationRequest);

    SpecializationResponse deleteSpecialization(UUID specializationId);

}
