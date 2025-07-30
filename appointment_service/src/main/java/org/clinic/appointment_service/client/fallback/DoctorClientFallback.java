package org.clinic.appointment_service.client.fallback;

import lombok.extern.slf4j.Slf4j;
import org.clinic.appointment_service.client.DoctorClient;
import org.clinic.common_service_web.dto.DepartmentResponse;
import org.clinic.common_service_web.dto.DoctorResponse;
import org.clinic.common_service_web.wrapper.dto.BaseResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class DoctorClientFallback implements DoctorClient {

    @Override
    public BaseResponse<DoctorResponse> getDoctorById(UUID id) {
        log.error("Doctor service is down. Returning default response");
        return null;
    }

    @Override
    public BaseResponse<List<DoctorResponse>> getDoctorsByListId(List<UUID> id) {
        log.error("Doctor service is down. Returning default response");
        return null;
    }

    @Override
    public BaseResponse<DepartmentResponse> geDepartmentById(UUID id) {
        log.error("Doctor service is down. Returning default response");
        return null;
    }

    @Override
    public BaseResponse<List<DepartmentResponse>> getDepartmentByListId(List<UUID> id) {
        log.error("Doctor service is down. Returning default response");
        return null;
    }

    @Override
    public String test() {
        return "Fixed response";
    }
}