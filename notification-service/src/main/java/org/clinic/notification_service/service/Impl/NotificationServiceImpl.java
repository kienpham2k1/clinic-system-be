package org.clinic.notification_service.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.clinic.common_service_web.exception.NotFoundException;
import org.clinic.common_service_web.localeTimeZone.service.MessageService;
import org.clinic.notification_service.dto.request.NotificationRequest;
import org.clinic.notification_service.dto.response.NotificationResponse;
import org.clinic.notification_service.enums.EventType;
import org.clinic.notification_service.enums.NotificationStatus;
import org.clinic.notification_service.enums.OutboxStatus;
import org.clinic.notification_service.kafka.KafkaProducerService;
import org.clinic.notification_service.mapper.NotificationMapper;
import org.clinic.notification_service.model.sql.NotificationEntity;
import org.clinic.notification_service.model.sql.OutboxMessageEntity;
import org.clinic.notification_service.repository.NotificationRepository;
import org.clinic.notification_service.repository.OutboxMessageRepository;
import org.clinic.notification_service.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final MessageService messageService;
    private final OutboxMessageRepository outboxMessageRepository;
    private final ObjectMapper objectMapper;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public Page<NotificationResponse> getNotificationByPage(Pageable pageable) {
        var notificationEntityPage = notificationRepository.findAll(pageable);
        Page<NotificationResponse> notificationResponsePage = NotificationMapper.INSTANCE.mapPage(notificationEntityPage, NotificationMapper.INSTANCE::toDtoResponse);
        return notificationResponsePage;
    }

    @Override
    public NotificationResponse getNotificationById(UUID notificationId) {
        Optional<NotificationEntity> notificationEntity = notificationRepository.findById(notificationId);
        if (notificationEntity.isPresent()) {
            return NotificationMapper.INSTANCE.toDtoResponse(notificationEntity.get());
        } else
            throw new NotFoundException(messageService.translate("notification.not-found", new Object[]{notificationId}));
    }

    @Override
    public NotificationResponse insertNotification(NotificationRequest notificationRequest) throws JsonProcessingException {
        String metadata = objectMapper.writeValueAsString(notificationRequest.getMetadata());
        NotificationEntity notificationEntity = NotificationMapper.INSTANCE.toEntity(notificationRequest);
        notificationEntity.setStatus(NotificationStatus.PENDING);
        notificationEntity.setMetadata(metadata);
        notificationRepository.save(notificationEntity);
        String payload = objectMapper.writeValueAsString(notificationRequest);
        OutboxMessageEntity outbox = OutboxMessageEntity.builder()
                .eventType(EventType.COMMON_NOTIFICATION)
                .payload(payload)
                .status(OutboxStatus.PENDING)
                .build();
        outboxMessageRepository.save(outbox);
        return NotificationMapper.INSTANCE.toDtoResponse(notificationEntity);
    }

    @Override
    public NotificationResponse updateNotification(UUID notificationId, NotificationRequest notificationRequest) {
        Optional<NotificationEntity> notificationEntity = notificationRepository.findById(notificationId);
        if (notificationEntity.isPresent()) {
            NotificationMapper.INSTANCE.updateEntityFromRequest(notificationRequest, notificationEntity.get());
            return NotificationMapper.INSTANCE.toDtoResponse(notificationRepository.saveAndFlush(notificationEntity.get()));
        } else
            throw new NotFoundException(messageService.translate("notification.not-found", new Object[]{notificationId}));
    }

    @Override
    public NotificationResponse deleteNotification(UUID notificationId) {
        Optional<NotificationEntity> NotificationEntity = notificationRepository.findById(notificationId);
        if (NotificationEntity.isPresent()) {
            notificationRepository.delete(NotificationEntity.get());
            return NotificationMapper.INSTANCE.toDtoResponse(NotificationEntity.get());
        } else
            throw new NotFoundException(messageService.translate("notification.not-found", new Object[]{notificationId}));
    }

    @Override
    public List<NotificationResponse> getNotificationsList(List<UUID> notificationIds) {
        List<NotificationEntity> notificationResponses = notificationRepository.findAllById(notificationIds);
        return NotificationMapper.INSTANCE.toDtoList(notificationResponses);
    }

    @Override
    public NotificationResponse markAsRead(UUID notificationId) {
        NotificationEntity notificationEntity = notificationRepository.findById(notificationId).orElseThrow(() ->
                new NotFoundException(messageService.translate("notification.not-found", new Object[]{notificationId}))
        );
        notificationEntity.setStatus(NotificationStatus.READ);
        notificationRepository.save(notificationEntity);
        return NotificationMapper.INSTANCE.toDtoResponse(notificationEntity);
    }
}
