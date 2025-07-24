package org.clinic.patientservice.service.impl;

import org.clinic.commonserviceweb.exception.NotFoundException;
import org.clinic.commonserviceweb.service.MessageService;
import org.clinic.patientservice.dto.request.PatientRequestDto;
import org.clinic.patientservice.dto.response.PatientResponseDto;
import org.clinic.patientservice.mapper.PatientMapper;
import org.clinic.patientservice.model.sql.PatientEntity;
import org.clinic.patientservice.repository.PatientRepository;
import org.clinic.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private MessageService translateService;
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Page<PatientResponseDto> getPatientsPage(Pageable pageable) {
        var patientEntityPage = patientRepository.findAll(pageable);
        Page<PatientResponseDto> patientResponseDtoPage = PatientMapper.INSTANCE.mapPage(patientEntityPage, PatientMapper.INSTANCE::toDtoResponse);
        return patientResponseDtoPage;
    }

    @Override
    public PatientResponseDto getPatientById(UUID patientId) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(patientId);
        if (patientEntity.isPresent()) {
            return PatientMapper.INSTANCE.toDtoResponse(patientEntity.get());
        } else throw new NotFoundException(translateService.translate("patient.not-found", new Object[]{patientId}));
    }

    @Override
    public PatientResponseDto insertPatient(PatientRequestDto patientRequestDto) {
        PatientEntity patientEntity = PatientMapper.INSTANCE.toEntity(patientRequestDto);
        patientRepository.save(patientEntity);
        return PatientMapper.INSTANCE.toDtoResponse(patientEntity);
    }

    @Override
    public PatientResponseDto updatePatient(UUID patientId, PatientRequestDto patientRequestDto) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(patientId);
        if (patientEntity.isPresent()) {
            PatientMapper.INSTANCE.updateEntityFromRequest(patientRequestDto, patientEntity.get());
            return PatientMapper.INSTANCE.toDtoResponse(patientRepository.saveAndFlush(patientEntity.get()));
        } else throw new NotFoundException(translateService.translate("patient.not-found", new Object[]{patientId}));
    }

    @Override
    public PatientResponseDto deletePatient(UUID patientId) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(patientId);
        if (patientEntity.isPresent()) {
            patientRepository.delete(patientEntity.get());
            return PatientMapper.INSTANCE.toDtoResponse(patientEntity.get());
        } else throw new NotFoundException(translateService.translate("patient.not-found", new Object[]{patientId}));
    }
}
