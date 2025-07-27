package org.clinic.authservice.mapper;

import org.clinic.authservice.dto.request.RoleRequest;
import org.clinic.authservice.dto.request.UserRegisterRequest;
import org.clinic.authservice.dto.response.RoleResponse;
import org.clinic.authservice.dto.response.UserResponse;
import org.clinic.authservice.model.sql.RoleEntity;
import org.clinic.authservice.model.sql.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleResponse toDtoResponse(RoleEntity entity);

    RoleEntity toEntity(RoleRequest dto);

    List<RoleResponse> toDtoList(List<RoleEntity> entities);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(RoleRequest from, @MappingTarget RoleEntity to);

    default <T, D> Page<D> mapPage(Page<T> sourcePage, Function<T, D> mapper) {
        List<D> content = sourcePage.getContent().stream()
                .map(mapper)
                .toList();

        return new PageImpl<>(content, sourcePage.getPageable(), sourcePage.getTotalElements());
    }
}
