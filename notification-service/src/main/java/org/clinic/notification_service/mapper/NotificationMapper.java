package org.clinic.notification_service.mapper;

import org.clinic.notification_service.dto.request.NotificationRequest;
import org.clinic.notification_service.dto.response.NotificationResponse;
import org.clinic.notification_service.model.NotificationEvent;
import org.clinic.notification_service.model.sql.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationResponse toDtoResponse(NotificationEntity entity);

    @Mapping(target = "metadata", ignore = true)
    NotificationEntity toEntity(NotificationRequest dto);

    NotificationEvent toEvent(NotificationRequest dto);

    @Mapping(target = "metadata", ignore = true)
    NotificationEvent toEvent(NotificationEntity entity);

    List<NotificationResponse> toDtoList(List<NotificationEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "metadata", ignore = true)
    void updateEntityFromRequest(NotificationRequest from, @MappingTarget NotificationEntity to);

    default <T, D> Page<D> mapPage(Page<T> sourcePage, Function<T, D> mapper) {
        List<D> content = sourcePage.getContent().stream()
                .map(mapper)
                .toList();

        return new PageImpl<>(content, sourcePage.getPageable(), sourcePage.getTotalElements());
    }
}
