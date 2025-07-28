package org.clinic.authservice.service.Impl;

import jakarta.transaction.Transactional;
import org.clinic.authservice.dto.request.UserRegisterRequest;
import org.clinic.authservice.dto.request.UserUpdateRequest;
import org.clinic.authservice.dto.response.UserResponse;
import org.clinic.authservice.mapper.UserMapper;
import org.clinic.authservice.model.sql.Authorize;
import org.clinic.authservice.model.sql.AuthorizeId;
import org.clinic.authservice.model.sql.RoleEntity;
import org.clinic.authservice.model.sql.UserEntity;
import org.clinic.authservice.repository.AuthorizeRepository;
import org.clinic.authservice.repository.RoleRepository;
import org.clinic.authservice.repository.UserRepository;
import org.clinic.authservice.service.UserService;
import org.clinic.common_security.security.enums.Role;
import org.clinic.common_service_web.exception.DuplicateException;
import org.clinic.common_service_web.exception.NotFoundException;
import org.clinic.common_service_web.localeTimeZone.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorizeRepository authorizeRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<UserResponse> getUserByPage(Pageable pageable) {
        var userEntityPage = userRepository.findAll(pageable);
        Page<UserResponse> userResponsesPage = UserMapper.INSTANCE.mapPage(userEntityPage, UserMapper.INSTANCE::toDtoResponse);
        return userResponsesPage;
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()) {
            return UserMapper.INSTANCE.toDtoResponse(userEntity.get());
        } else throw new NotFoundException(messageService.translate("user.not-found", new Object[]{username}));
    }

    @Override
    public UserResponse getUserById(UUID userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            return UserMapper.INSTANCE.toDtoResponse(userEntity.get());
        } else throw new NotFoundException(messageService.translate("user.not-found", new Object[]{userId}));
    }

    @Override
    @Transactional
    public UserResponse insertUser(UserRegisterRequest user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DuplicateException(messageService.translate("user.duplicate", new Object[]{user.getUsername()}));
        }
        String password = passwordEncoder.encode(user.getPassword());
        UserEntity userEntity = UserMapper.INSTANCE.toEntity(user);
        userEntity.setPassword(password);
        userRepository.save(userEntity);

        RoleEntity roleEntity = roleRepository.findByName(Role.PATIENT);

        Authorize authorize = Authorize.builder()
                .authorizeId(AuthorizeId.builder()
                        .userId(userEntity.getId())
                        .roleId(roleEntity.getId())
                        .build())
                .user(userEntity)
                .role(roleEntity)
                .build();
        authorizeRepository.save(authorize);

        return UserMapper.INSTANCE.toDtoResponse(userEntity);
    }

    @Override
    public UserResponse updateUser(UUID userId, UserUpdateRequest userDto) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            UserMapper.INSTANCE.updateEntityFromRequest(userDto, userEntity.get());
            return UserMapper.INSTANCE.toDtoResponse(userRepository.saveAndFlush(userEntity.get()));
        } else throw new NotFoundException(messageService.translate("patient.not-found", new Object[]{userId}));
    }

    @Override
    public UserResponse deleteUser(UUID userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            userRepository.delete(userEntity.get());
            return UserMapper.INSTANCE.toDtoResponse(userEntity.get());
        } else throw new NotFoundException(messageService.translate("patient.not-found", new Object[]{userId}));
    }
}
