package org.clinic.authservice.service;


import org.clinic.authservice.dto.request.UserRegisterRequest;
import org.clinic.authservice.dto.request.UserUpdateRequest;
import org.clinic.authservice.dto.response.UserResponse;
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
