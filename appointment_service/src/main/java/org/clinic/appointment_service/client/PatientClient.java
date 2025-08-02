package org.clinic.appointment_service.client;

import org.clinic.appointment_service.client.fallback.PatientClientFallback;
import org.clinic.common_service_web.dto.response.PatientResponse;
import org.clinic.common_service_web.wrapper.dto.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "patient-service",
        fallback = PatientClientFallback.class)
public interface PatientClient {
    @GetMapping("/api/v1/patients/{id}")
    BaseResponse<PatientResponse> getPatientsById(@PathVariable("id") UUID id);

    @PostMapping("/api/v1/patients/get-list")
    BaseResponse<List<PatientResponse>> getPatientsByListId(@RequestBody List<UUID> id);
}
