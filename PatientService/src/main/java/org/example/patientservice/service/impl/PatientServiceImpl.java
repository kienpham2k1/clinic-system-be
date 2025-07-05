package org.example.patientservice.service.impl;

import org.example.patientservice.dto.request.PatientRequestDto;
import org.example.patientservice.dto.response.PatientResponseDto;
import org.example.patientservice.exception.NotFoundException;
import org.example.patientservice.mapper.PatientMapper;
import org.example.patientservice.model.sql.PatientEntity;
import org.example.patientservice.repository.PatientRepository;
import org.example.patientservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

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
        } else throw new NotFoundException("Not found patient with id " + patientId);
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
        } else throw new NotFoundException("Not found patient with id " + patientId);
    }

    @Override
    public PatientResponseDto deletePatient(UUID patientId) {
        Optional<PatientEntity> patientEntity = patientRepository.findById(patientId);
        if (patientEntity.isPresent()) {
            patientRepository.deleteById(patientId);
            return PatientMapper.INSTANCE.toDtoResponse(patientEntity.get());
        } else throw new NotFoundException("Not found patient with id " + patientId);
    }
}
