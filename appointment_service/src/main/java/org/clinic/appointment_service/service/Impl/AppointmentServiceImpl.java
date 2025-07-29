package org.clinic.appointment_service.service.Impl;

import org.clinic.appointment_service.client.DoctorClient;
import org.clinic.appointment_service.client.PatientClient;
import org.clinic.appointment_service.dto.request.AppointmentRequest;
import org.clinic.appointment_service.dto.response.AppointmentResponse;
import org.clinic.appointment_service.mapper.AppointmentMapper;
import org.clinic.appointment_service.model.sql.AppointmentEntity;
import org.clinic.appointment_service.repository.AppointmentRepository;
import org.clinic.appointment_service.service.AppointmentService;
import org.clinic.common_service_web.dto.DoctorResponse;
import org.clinic.common_service_web.dto.PatientResponse;
import org.clinic.common_service_web.exception.NotFoundException;
import org.clinic.common_service_web.localeTimeZone.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private DoctorClient doctorClient;

    @Autowired
    private PatientClient patientClient;

    @Override
    public Page<AppointmentResponse> getAppointmentByPage(Pageable pageable) {
        var appointmentEntityPage = appointmentRepository.findAll(pageable);
        Page<AppointmentResponse> appointmentResponsePage = AppointmentMapper.INSTANCE.mapPage(appointmentEntityPage, AppointmentMapper.INSTANCE::toDtoResponse);
        List<AppointmentResponse> appointmentResponseSet = appointmentResponsePage.getContent();

        Set<UUID> doctorIds = appointmentResponseSet.stream().map(AppointmentResponse::getDoctorId).collect(Collectors.toSet());
        Set<UUID> patientIds = appointmentResponseSet.stream().map(AppointmentResponse::getPatientId).collect(Collectors.toSet());

        List<DoctorResponse> doctorResponses = doctorClient.getDoctorsByListId(new ArrayList<>(doctorIds)).getData();
        List<PatientResponse> patientResponses = patientClient.getPatientsByListId(new ArrayList<>(patientIds)).getData();

        Map<UUID, DoctorResponse> doctorMap = doctorResponses.stream()
                .collect(Collectors.toMap(DoctorResponse::getId, d -> d));
        Map<UUID, PatientResponse> patientMap = patientResponses.stream()
                .collect(Collectors.toMap(PatientResponse::getId, p -> p));

        appointmentResponseSet.forEach(a -> {
            a.setDoctor(doctorMap.get(a.getDoctorId()));
            a.setPatient(patientMap.get(a.getPatientId()));
        });
        return appointmentResponsePage;
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
        } else
            throw new NotFoundException(messageService.translate("appointment.not-found", new Object[]{appointmentId}));
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
        } else
            throw new NotFoundException(messageService.translate("appointment.not-found", new Object[]{appointmentId}));
    }

    @Override
    public AppointmentResponse deleteAppointment(UUID appointmentId) {
        Optional<AppointmentEntity> AppointmentEntity = appointmentRepository.findById(appointmentId);
        if (AppointmentEntity.isPresent()) {
            appointmentRepository.delete(AppointmentEntity.get());
            return AppointmentMapper.INSTANCE.toDtoResponse(AppointmentEntity.get());
        } else
            throw new NotFoundException(messageService.translate("appointment.not-found", new Object[]{appointmentId}));
    }
}
