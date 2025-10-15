package org.clinic.doctor_service.service;

import org.clinic.doctor_service.dto.request.DepartmentRequest;
import org.clinic.doctor_service.dto.response.DepartmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    Page<DepartmentResponse> getDepartmentByPage(Pageable pageable);

    List<DepartmentResponse> getDepartments();

    DepartmentResponse getDepartmentById(UUID departmentId);

    DepartmentResponse insertDepartment(DepartmentRequest departmentRequest);

    DepartmentResponse updateDepartment(UUID departmentId, DepartmentRequest departmentRequest);

    DepartmentResponse deleteDepartment(UUID departmentId);

    List<DepartmentResponse> getDepartmentsList(List<UUID> departmentIds);
}
