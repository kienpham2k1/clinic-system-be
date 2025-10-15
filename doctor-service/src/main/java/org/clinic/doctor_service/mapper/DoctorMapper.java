package org.clinic.doctor_service.mapper;

import org.clinic.doctor_service.dto.request.DoctorRequest;
import org.clinic.doctor_service.dto.response.DoctorResponse;
import org.clinic.doctor_service.model.sql.DoctorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    DoctorResponse toDtoResponse(DoctorEntity entity);

    DoctorEntity toEntity(DoctorRequest dto);

    List<DoctorResponse> toDtoList(List<DoctorEntity> entities);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(DoctorRequest from, @MappingTarget DoctorEntity to);

    default <T, D> Page<D> mapPage(Page<T> sourcePage, Function<T, D> mapper) {
        List<D> content = sourcePage.getContent().stream()
                .map(mapper)
                .toList();

        return new PageImpl<>(content, sourcePage.getPageable(), sourcePage.getTotalElements());
    }
}
