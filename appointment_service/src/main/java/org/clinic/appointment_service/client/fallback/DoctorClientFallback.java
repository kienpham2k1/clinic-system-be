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
        // Trả về bản ghi giả hoặc thông báo lỗi
        log.info("getDoctorById fall back call");
        DoctorResponse doctor = new DoctorResponse();
        return new BaseResponse<DoctorResponse>("200", "321321",doctor);
    }

    @Override
    public BaseResponse<List<DoctorResponse>> getDoctorsByListId(List<UUID> id) {
        log.info("getDoctorById fall back call");
        DoctorResponse doctor = new DoctorResponse();
        return new BaseResponse<List<DoctorResponse>>("200", "321321",List.of(doctor));
    }

    @Override
    public BaseResponse<DepartmentResponse> geDepartmentById(UUID id) {
        return null;
    }

    @Override
    public BaseResponse<List<DepartmentResponse>> getDepartmentByListId(List<UUID> id) {
        return null;
    }
}