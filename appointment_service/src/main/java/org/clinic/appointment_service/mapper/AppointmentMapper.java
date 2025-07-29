package org.clinic.appointment_service.mapper;

import org.clinic.appointment_service.dto.request.AppointmentRequest;
import org.clinic.appointment_service.dto.response.AppointmentResponse;
import org.clinic.appointment_service.model.sql.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    AppointmentResponse toDtoResponse(AppointmentEntity entity);

    AppointmentEntity toEntity(AppointmentRequest dto);

    List<AppointmentResponse> toDtoList(List<AppointmentEntity> entities);

    @Mapping(   target = "id", ignore = true)
    void updateEntityFromRequest(AppointmentRequest from, @MappingTarget AppointmentEntity to);

    default <T, D> Page<D> mapPage(Page<T> sourcePage, Function<T, D> mapper) {
        List<D> content = sourcePage.getContent().stream()
                .map(mapper)
                .toList();

        return new PageImpl<>(content, sourcePage.getPageable(), sourcePage.getTotalElements());
    }
}
