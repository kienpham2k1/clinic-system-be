package org.clinic.doctor_service.mapper;

import org.clinic.doctor_service.dto.request.DepartmentRequest;
import org.clinic.doctor_service.dto.response.DepartmentResponse;
import org.clinic.doctor_service.model.sql.DepartmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    DepartmentResponse toDtoResponse(DepartmentEntity entity);

    DepartmentEntity toEntity(DepartmentRequest dto);

    List<DepartmentResponse> toDtoList(List<DepartmentEntity> entities);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(DepartmentRequest from, @MappingTarget DepartmentEntity to);

    default <T, D> Page<D> mapPage(Page<T> sourcePage, Function<T, D> mapper) {
        List<D> content = sourcePage.getContent().stream()
                .map(mapper)
                .toList();

        return new PageImpl<>(content, sourcePage.getPageable(), sourcePage.getTotalElements());
    }
}
