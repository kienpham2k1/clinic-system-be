package org.clinic.authservice.controller;

import org.clinic.authservice.dto.request.RoleRequest;
import org.clinic.authservice.dto.response.RoleResponse;
import org.clinic.authservice.service.RoleService;
import org.clinic.common_service_web.constant.PageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<Page<RoleResponse>> getRoleByPage(
            @RequestParam(name = "pageNo", defaultValue = PageConstant.PAGE_START) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = PageConstant.PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = PageConstant.PAGE_ORDER_BY) String sortBy,
            @RequestParam(name = "sortDirection", defaultValue = PageConstant.PAGE_ORDER_DIRECTION) String sortDirection
    ) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return ResponseEntity.ok(roleService.getRoleByPage(pageable));
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable(name = "roleId") UUID roleId) {
        return ResponseEntity.ok(roleService.getRoleById(roleId));
    }

    @PostMapping
    public ResponseEntity<RoleResponse> insertRole(@RequestBody RoleRequest roleDto) {
        return ResponseEntity.ok(roleService.insertRole(roleDto));
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable(name = "roleId") UUID roleId,
                                                   @RequestBody RoleRequest roleDto) {
        return ResponseEntity.ok(roleService.updateRole(roleId, roleDto));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<RoleResponse> deleteRole(@PathVariable(name = "roleId") UUID roleId) {
        return ResponseEntity.ok(roleService.deleteRole(roleId));
    }
}
