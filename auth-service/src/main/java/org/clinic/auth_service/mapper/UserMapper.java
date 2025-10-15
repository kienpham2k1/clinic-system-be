package org.clinic.auth_service.mapper;

import org.clinic.auth_service.dto.request.UserRegisterRequest;
import org.clinic.auth_service.dto.request.UserUpdateRequest;
import org.clinic.auth_service.dto.response.UserResponse;
import org.clinic.auth_service.model.sql.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.function.Function;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse toDtoResponse(UserEntity entity);

    UserEntity toEntity(UserRegisterRequest dto);

    List<UserResponse> toDtoList(List<UserEntity> entities);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(UserUpdateRequest from, @MappingTarget UserEntity to);

    default <T, D> Page<D> mapPage(Page<T> sourcePage, Function<T, D> mapper) {
        List<D> content = sourcePage.getContent().stream()
                .map(mapper)
                .toList();

        return new PageImpl<>(content, sourcePage.getPageable(), sourcePage.getTotalElements());
    }
}
