package org.clinic.appointment_service.client;

import org.clinic.common_service_web.dto.DoctorResponse;
import org.clinic.common_service_web.wrapper.dto.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "doctor-service")
public interface DoctorClient {
    @GetMapping("/api/v1/doctors/{id}")
    BaseResponse<DoctorResponse> getDoctorById(@PathVariable("id") UUID id);

    @PostMapping("/api/v1/doctors/get-list")
    BaseResponse<List<DoctorResponse>> getDoctorsByListId(@RequestBody List<UUID> id);
}
