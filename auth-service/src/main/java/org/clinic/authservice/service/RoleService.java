package org.clinic.authservice.service;

import org.clinic.authservice.dto.request.RoleRequest;
import org.clinic.authservice.dto.request.UserRegisterRequest;
import org.clinic.authservice.dto.request.UserUpdateRequest;
import org.clinic.authservice.dto.response.RoleResponse;
import org.clinic.authservice.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    Page<RoleResponse> getRoleByPage(Pageable pageable);

    List<RoleResponse> getRoleAsList();

    RoleResponse getRoleById(UUID roleId);

    RoleResponse insertRole(RoleRequest role);

    RoleResponse updateRole(UUID roleId, RoleRequest role);

    RoleResponse deleteRole(UUID roleId);
}
