package org.clinic.doctor_service.service.Impl;

import org.clinic.common_service_web.exception.NotFoundException;
import org.clinic.common_service_web.localeTimeZone.service.MessageService;
import org.clinic.doctor_service.dto.request.DoctorRequest;
import org.clinic.doctor_service.dto.response.DoctorResponse;
import org.clinic.doctor_service.mapper.DoctorMapper;
import org.clinic.doctor_service.model.sql.DoctorEntity;
import org.clinic.doctor_service.repository.DoctorRepository;
import org.clinic.doctor_service.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private MessageService messageService;


    @Override
    public Page<DoctorResponse> getDoctorByPage(Pageable pageable) {
        var doctorEntityPage = doctorRepository.findAll(pageable);
        Page<DoctorResponse> doctorResponsePage = DoctorMapper.INSTANCE.mapPage(doctorEntityPage, DoctorMapper.INSTANCE::toDtoResponse);
        return doctorResponsePage;
    }

    @Override
    public List<DoctorResponse> getDoctors() {
        return DoctorMapper.INSTANCE.toDtoList(doctorRepository.findAll());
    }

    @Override
    public DoctorResponse getDoctorById(UUID doctorId) {
        Optional<DoctorEntity> doctorEntity = doctorRepository.findById(doctorId);
        if (doctorEntity.isPresent()) {
            return DoctorMapper.INSTANCE.toDtoResponse(doctorEntity.get());
        } else throw new NotFoundException(messageService.translate("doctor.not-found", new Object[]{doctorId}));
    }

    @Override
    public DoctorResponse insertDoctor(DoctorRequest doctorRequest) {
        DoctorEntity doctorEntity = DoctorMapper.INSTANCE.toEntity(doctorRequest);
        doctorRepository.save(doctorEntity);
        return DoctorMapper.INSTANCE.toDtoResponse(doctorEntity);
    }

    @Override
    public DoctorResponse updateDoctor(UUID doctorId, DoctorRequest doctorRequest) {
        Optional<DoctorEntity> doctorEntity = doctorRepository.findById(doctorId);
        if (doctorEntity.isPresent()) {
            DoctorMapper.INSTANCE.updateEntityFromRequest(doctorRequest, doctorEntity.get());
            return DoctorMapper.INSTANCE.toDtoResponse(doctorRepository.saveAndFlush(doctorEntity.get()));
        } else throw new NotFoundException(messageService.translate("doctor.not-found", new Object[]{doctorId}));
    }

    @Override
    public DoctorResponse deleteDoctor(UUID doctorId) {
        Optional<DoctorEntity> DoctorEntity = doctorRepository.findById(doctorId);
        if (DoctorEntity.isPresent()) {
            doctorRepository.delete(DoctorEntity.get());
            return DoctorMapper.INSTANCE.toDtoResponse(DoctorEntity.get());
        } else throw new NotFoundException(messageService.translate("doctor.not-found", new Object[]{doctorId}));
    }

    @Override
    public List<DoctorResponse> getDoctorsList(List<UUID> doctorIds) {
        List<DoctorEntity> doctorResponses = doctorRepository.findAllById(doctorIds);
        return DoctorMapper.INSTANCE.toDtoList(doctorResponses);
    }
}
