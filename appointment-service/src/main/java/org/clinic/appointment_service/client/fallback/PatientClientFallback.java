package org.clinic.appointment_service.client.fallback;

import lombok.extern.slf4j.Slf4j;
import org.clinic.appointment_service.client.PatientClient;
import org.clinic.common_service_web.dto.response.PatientResponse;
import org.clinic.common_service_web.wrapper.dto.BaseResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class PatientClientFallback implements PatientClient {
    @Override
    public BaseResponse<PatientResponse> getPatientsById(UUID id) {
        return null;
    }

    @Override
    public BaseResponse<List<PatientResponse>> getPatientsByListId(List<UUID> id) {
        return null;
    }
}
