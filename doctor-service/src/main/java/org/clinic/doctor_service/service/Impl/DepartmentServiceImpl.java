package org.clinic.doctor_service.service.Impl;

import org.clinic.common_service_web.exception.NotFoundException;
import org.clinic.common_service_web.localeTimeZone.service.MessageService;
import org.clinic.doctor_service.dto.request.DepartmentRequest;
import org.clinic.doctor_service.dto.response.DepartmentResponse;
import org.clinic.doctor_service.mapper.DepartmentMapper;
import org.clinic.doctor_service.model.sql.DepartmentEntity;
import org.clinic.doctor_service.repository.DepartmentRepository;
import org.clinic.doctor_service.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public Page<DepartmentResponse> getDepartmentByPage(Pageable pageable) {
        var departmentEntityPage = departmentRepository.findAll(pageable);
        Page<DepartmentResponse> departmentResponses = DepartmentMapper.INSTANCE.mapPage(departmentEntityPage, DepartmentMapper.INSTANCE::toDtoResponse);
        return departmentResponses;
    }

    @Override
    public List<DepartmentResponse> getDepartments() {
        return DepartmentMapper.INSTANCE.toDtoList(departmentRepository.findAll());
    }

    @Override
    public DepartmentResponse getDepartmentById(UUID departmentId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        if (departmentEntity.isPresent()) {
            return DepartmentMapper.INSTANCE.toDtoResponse(departmentEntity.get());
        } else
            throw new NotFoundException(messageService.translate("department.not-found", new Object[]{departmentId}));
    }

    @Override
    public DepartmentResponse insertDepartment(DepartmentRequest departmentRequest) {
        DepartmentEntity departmentEntity = DepartmentMapper.INSTANCE.toEntity(departmentRequest);
        departmentRepository.save(departmentEntity);
        return DepartmentMapper.INSTANCE.toDtoResponse(departmentEntity);
    }

    @Override
    public DepartmentResponse updateDepartment(UUID departmentId, DepartmentRequest departmentRequest) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        if (departmentEntity.isPresent()) {
            DepartmentMapper.INSTANCE.updateEntityFromRequest(departmentRequest, departmentEntity.get());
            return DepartmentMapper.INSTANCE.toDtoResponse(departmentRepository.saveAndFlush(departmentEntity.get()));
        } else
            throw new NotFoundException(messageService.translate("department.not-found", new Object[]{departmentId}));
    }

    @Override
    public DepartmentResponse deleteDepartment(UUID departmentId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        if (departmentEntity.isPresent()) {
            departmentRepository.delete(departmentEntity.get());
            return DepartmentMapper.INSTANCE.toDtoResponse(departmentEntity.get());
        } else
            throw new NotFoundException(messageService.translate("department.not-found", new Object[]{departmentId}));
    }

    @Override
    public List<DepartmentResponse> getDepartmentsList(List<UUID> departmentIds) {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAllById(departmentIds);
        return DepartmentMapper.INSTANCE.toDtoList(departmentEntities);
    }
}
