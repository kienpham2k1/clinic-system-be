package org.clinic.appointment_service.service.Impl;

import org.clinic.appointment_service.dto.response.AppointmentResponse;
import org.clinic.appointment_service.mapper.AppointmentMapper;
import org.clinic.appointment_service.repository.AppointmentRepository;
import org.clinic.appointment_service.service.AppointmentService;
import org.clinic.appointment_service.model.sql.AppointmentEntity;
import org.clinic.common_service_web.exception.NotFoundException;
import org.clinic.common_service_web.localeTimeZone.service.MessageService;
import org.clinic.appointment_service.dto.request.AppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MessageService messageService;


    @Override
    public Page<AppointmentResponse> getAppointmentByPage(Pageable pageable) {
        var appointmentEntityPage = appointmentRepository.findAll(pageable);
        Page<AppointmentResponse> userResponsesPage = AppointmentMapper.INSTANCE.mapPage(appointmentEntityPage, AppointmentMapper.INSTANCE::toDtoResponse);
        return userResponsesPage;
    }

    @Override
    public List<AppointmentResponse> getAppointments() {
        return AppointmentMapper.INSTANCE.toDtoList(appointmentRepository.findAll());
    }

    @Override
    public AppointmentResponse getAppointmentById(UUID appointmentId) {
        Optional<AppointmentEntity> appointmentEntity = appointmentRepository.findById(appointmentId);
        if (appointmentEntity.isPresent()) {
            return AppointmentMapper.INSTANCE.toDtoResponse(appointmentEntity.get());
        } else throw new NotFoundException(messageService.translate("appointment.not-found", new Object[]{appointmentId}));
    }

    @Override
    public AppointmentResponse insertAppointment(AppointmentRequest appointmentRequest) {
        AppointmentEntity appointmentEntity = AppointmentMapper.INSTANCE.toEntity(appointmentRequest);
        appointmentRepository.save(appointmentEntity);
        return AppointmentMapper.INSTANCE.toDtoResponse(appointmentEntity);
    }

    @Override
    public AppointmentResponse updateAppointment(UUID appointmentId, AppointmentRequest appointmentRequest) {
        Optional<AppointmentEntity> appointmentEntity = appointmentRepository.findById(appointmentId);
        if (appointmentEntity.isPresent()) {
            AppointmentMapper.INSTANCE.updateEntityFromRequest(appointmentRequest, appointmentEntity.get());
            return AppointmentMapper.INSTANCE.toDtoResponse(appointmentRepository.saveAndFlush(appointmentEntity.get()));
        } else throw new NotFoundException(messageService.translate("appointment.not-found", new Object[]{appointmentId}));
    }

    @Override
    public AppointmentResponse deleteAppointment(UUID appointmentId) {
        Optional<AppointmentEntity> AppointmentEntity = appointmentRepository.findById(appointmentId);
        if (AppointmentEntity.isPresent()) {
            appointmentRepository.delete(AppointmentEntity.get());
            return AppointmentMapper.INSTANCE.toDtoResponse(AppointmentEntity.get());
        } else throw new NotFoundException(messageService.translate("appointment.not-found", new Object[]{appointmentId}));
    }
}
