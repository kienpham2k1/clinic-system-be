package org.clinic.authservice.service.Impl;

import org.clinic.authservice.dto.request.RoleRequest;
import org.clinic.authservice.dto.response.RoleResponse;
import org.clinic.authservice.mapper.RoleMapper;
import org.clinic.authservice.model.sql.RoleEntity;
import org.clinic.authservice.repository.RoleRepository;
import org.clinic.authservice.service.RoleService;
import org.clinic.commonserviceweb.exception.NotFoundException;
import org.clinic.commonserviceweb.localeTimeZone.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MessageService messageService;

    @Override
    public Page<RoleResponse> getRoleByPage(Pageable pageable) {
        return RoleMapper.INSTANCE.mapPage(roleRepository.findAll(pageable), RoleMapper.INSTANCE::toDtoResponse);
    }

    @Override
    public List<RoleResponse> getRoleAsList() {
        return List.of();
    }

    @Override
    public RoleResponse getRoleById(UUID roleId) {
        Optional<RoleEntity> roleEntity = roleRepository.findById(roleId);
        if (roleEntity.isPresent()) {
            return RoleMapper.INSTANCE.toDtoResponse(roleEntity.get());
        } else throw new NotFoundException(messageService.translate("role.not-found", new Object[]{roleId}));
    }

    @Override
    public RoleResponse insertRole(RoleRequest role) {
        RoleEntity roleEntity = RoleMapper.INSTANCE.toEntity(role);
        roleRepository.save(roleEntity);
        return RoleMapper.INSTANCE.toDtoResponse(roleEntity);
    }

    @Override
    public RoleResponse updateRole(UUID roleId, RoleRequest role) {
        Optional<RoleEntity> roleEntity = roleRepository.findById(roleId);
        if (roleEntity.isPresent()) {
            RoleMapper.INSTANCE.updateEntityFromRequest(role, roleEntity.get());
            return RoleMapper.INSTANCE.toDtoResponse(roleRepository.save(roleEntity.get()));
        } else throw new NotFoundException(messageService.translate("patient.not-found", new Object[]{roleId}));
    }

    @Override
    public RoleResponse deleteRole(UUID roleId) {
        Optional<RoleEntity> roleEntity = roleRepository.findById(roleId);
        if (roleEntity.isPresent()) {
            roleRepository.delete(roleEntity.get());
            return RoleMapper.INSTANCE.toDtoResponse(roleRepository.save(roleEntity.get()));
        } else throw new NotFoundException(messageService.translate("patient.not-found", new Object[]{roleId}));
    }
}
