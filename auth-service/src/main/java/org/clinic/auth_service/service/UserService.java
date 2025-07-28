package org.clinic.auth_service.service;


import org.clinic.auth_service.dto.request.UserRegisterRequest;
import org.clinic.auth_service.dto.request.UserUpdateRequest;
import org.clinic.auth_service.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    Page<UserResponse> getUserByPage(Pageable pageable);

    UserResponse getUserByUsername(String username);

    UserResponse getUserById(UUID userId);

    UserResponse insertUser(UserRegisterRequest user);

    UserResponse updateUser(UUID userId, UserUpdateRequest user);

    UserResponse deleteUser(UUID UserId);

}
