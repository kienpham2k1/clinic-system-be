package org.clinic.doctorservice.service.Impl;

import org.clinic.common_service_web.exception.NotFoundException;
import org.clinic.common_service_web.localeTimeZone.service.MessageService;
import org.clinic.doctorservice.dto.request.DoctorRequest;
import org.clinic.doctorservice.dto.response.DoctorResponse;
import org.clinic.doctorservice.mapper.DoctorMapper;
import org.clinic.doctorservice.model.sql.DoctorEntity;
import org.clinic.doctorservice.repository.DoctorRepository;
import org.clinic.doctorservice.service.DoctorService;
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
        Page<DoctorResponse> userResponsesPage = DoctorMapper.INSTANCE.mapPage(doctorEntityPage, DoctorMapper.INSTANCE::toDtoResponse);
        return userResponsesPage;
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
}
