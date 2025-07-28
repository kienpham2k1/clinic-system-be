package org.clinic.auth_service.service;

import org.clinic.auth_service.dto.request.RoleRequest;
import org.clinic.auth_service.dto.response.RoleResponse;
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
