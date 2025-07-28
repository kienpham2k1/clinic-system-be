package org.clinic.doctorservice.service;

import org.clinic.doctorservice.dto.request.DoctorRequest;
import org.clinic.doctorservice.dto.response.DoctorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    Page<DoctorResponse> getDoctorByPage(Pageable pageable);

    List<DoctorResponse> getDoctors();

    DoctorResponse getDoctorById(UUID doctorId);

    DoctorResponse insertDoctor(DoctorRequest doctorRequest);

    DoctorResponse updateDoctor(UUID doctorId, DoctorRequest doctorRequest);

    DoctorResponse deleteDoctor(UUID doctorId);

}
