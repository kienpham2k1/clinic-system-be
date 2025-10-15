package org.clinic.doctor_service.service.Impl;

import org.clinic.common_service_web.exception.NotFoundException;
import org.clinic.common_service_web.localeTimeZone.service.MessageService;
import org.clinic.doctor_service.dto.request.SpecializationRequest;
import org.clinic.doctor_service.dto.response.SpecializationResponse;
import org.clinic.doctor_service.mapper.SpecializationMapper;
import org.clinic.doctor_service.model.sql.SpecializationEntity;
import org.clinic.doctor_service.repository.SpecializationRepository;
import org.clinic.doctor_service.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SpecializationServiceImpl implements SpecializationService {
    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public Page<SpecializationResponse> getSpecializationByPage(Pageable pageable) {
        var specializationEntityPage = specializationRepository.findAll(pageable);
        Page<SpecializationResponse> specializationResponses = SpecializationMapper.INSTANCE.mapPage(specializationEntityPage, SpecializationMapper.INSTANCE::toDtoResponse);
        return specializationResponses;
    }

    @Override
    public List<SpecializationResponse> getSpecializations() {
        return SpecializationMapper.INSTANCE.toDtoList(specializationRepository.findAll());
    }

    @Override
    public SpecializationResponse getSpecializationById(UUID specializationId) {
        Optional<SpecializationEntity> specializationEntity = specializationRepository.findById(specializationId);
        if (specializationEntity.isPresent()) {
            return SpecializationMapper.INSTANCE.toDtoResponse(specializationEntity.get());
        } else
            throw new NotFoundException(messageService.translate("specialization.not-found", new Object[]{specializationId}));
    }

    @Override
    public SpecializationResponse insertSpecialization(SpecializationRequest specializationRequest) {
        SpecializationEntity specializationEntity = SpecializationMapper.INSTANCE.toEntity(specializationRequest);
        specializationRepository.save(specializationEntity);
        return SpecializationMapper.INSTANCE.toDtoResponse(specializationEntity);
    }

    @Override
    public SpecializationResponse updateSpecialization(UUID specializationId, SpecializationRequest specializationRequest) {
        Optional<SpecializationEntity> specializationEntity = specializationRepository.findById(specializationId);
        if (specializationEntity.isPresent()) {
            SpecializationMapper.INSTANCE.updateEntityFromRequest(specializationRequest, specializationEntity.get());
            return SpecializationMapper.INSTANCE.toDtoResponse(specializationRepository.saveAndFlush(specializationEntity.get()));
        } else
            throw new NotFoundException(messageService.translate("specialization.not-found", new Object[]{specializationId}));
    }

    @Override
    public SpecializationResponse deleteSpecialization(UUID specializationId) {
        Optional<SpecializationEntity> specializationEntity = specializationRepository.findById(specializationId);
        if (specializationEntity.isPresent()) {
            specializationRepository.delete(specializationEntity.get());
            return SpecializationMapper.INSTANCE.toDtoResponse(specializationEntity.get());
        } else
            throw new NotFoundException(messageService.translate("specialization.not-found", new Object[]{specializationId}));
    }
}
