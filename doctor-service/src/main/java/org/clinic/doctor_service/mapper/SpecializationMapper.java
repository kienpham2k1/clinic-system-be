package org.clinic.doctor_service.mapper;

import org.clinic.doctor_service.dto.request.SpecializationRequest;
import org.clinic.doctor_service.dto.response.SpecializationResponse;
import org.clinic.doctor_service.model.sql.SpecializationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface SpecializationMapper {
    SpecializationMapper INSTANCE = Mappers.getMapper(SpecializationMapper.class);

    SpecializationResponse toDtoResponse(SpecializationEntity entity);

    SpecializationEntity toEntity(SpecializationRequest dto);

    List<SpecializationResponse> toDtoList(List<SpecializationEntity> entities);

    @Mapping(   target = "id", ignore = true)
    void updateEntityFromRequest(SpecializationRequest from, @MappingTarget SpecializationEntity to);

    default <T, D> Page<D> mapPage(Page<T> sourcePage, Function<T, D> mapper) {
        List<D> content = sourcePage.getContent().stream()
                .map(mapper)
                .toList();

        return new PageImpl<>(content, sourcePage.getPageable(), sourcePage.getTotalElements());
    }
}
