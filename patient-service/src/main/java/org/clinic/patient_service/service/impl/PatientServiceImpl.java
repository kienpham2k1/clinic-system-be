package org.clinic.patient_service.service.impl;

import org.clinic.common_service_web.exception.NotFoundException;
import org.clinic.common_service_web.localeTimeZone.service.MessageService;
import org.clinic.patient_service.dto.request.PatientRequestDto;
import org.clinic.patient_service.dto.response.PatientResponseDto;
import org.clinic.patient_service.mapper.PatientMapper;
import org.clinic.patient_service.model.sql.PatientEntity;
import org.clinic.patient_service.repository.PatientRepository;
import org.clinic.patient_service.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private MessageService messageService;
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
        } else throw new NotFoundException(messageService.translate("patient.not-found", new Object[]{patientId}));
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
        } else throw new NotFoundException(messageService.translate("patient.not-found", new Object[]{patientId}));
    }

    @Override
    public PatientResponseDto deletePatient(UUID patientId) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(patientId);
        if (patientEntity.isPresent()) {
            patientRepository.delete(patientEntity.get());
            return PatientMapper.INSTANCE.toDtoResponse(patientEntity.get());
        } else throw new NotFoundException(messageService.translate("patient.not-found", new Object[]{patientId}));
    }

    @Override
    public List<PatientResponseDto> getPatientsList(List<UUID> patientIds) {
        List<PatientEntity> patientEntities = patientRepository.findAllById(patientIds);
        return PatientMapper.INSTANCE.toDtoList(patientEntities);
    }
}
