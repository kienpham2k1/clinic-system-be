package org.clinic.patient_service.mapper;

import org.clinic.patient_service.dto.request.PatientRequestDto;
import org.clinic.patient_service.dto.response.PatientResponseDto;
import org.clinic.patient_service.model.sql.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    PatientResponseDto toDtoResponse(PatientEntity entity);

    PatientEntity toEntity(PatientRequestDto dto);

    List<PatientResponseDto> toDtoList(List<PatientEntity> entities);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(PatientRequestDto from, @MappingTarget PatientEntity to);

    default <T, D> Page<D> mapPage(Page<T> sourcePage, Function<T, D> mapper) {
        List<D> content = sourcePage.getContent().stream()
                .map(mapper)
                .toList();

        return new PageImpl<>(content, sourcePage.getPageable(), sourcePage.getTotalElements());
    }
}
