package org.clinic.authservice.service.Impl;

import org.clinic.authservice.dto.request.UserRegisterRequest;
import org.clinic.authservice.dto.request.UserUpdateRequest;
import org.clinic.authservice.dto.response.UserResponse;
import org.clinic.authservice.mapper.UserMapper;
import org.clinic.authservice.model.sql.UserEntity;
import org.clinic.authservice.repository.UserRepository;
import org.clinic.authservice.service.UserService;
import org.clinic.commonserviceweb.exception.NotFoundException;
import org.clinic.commonserviceweb.localeTimeZone.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

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
    public UserResponse insertUser(UserRegisterRequest user) {
        UserEntity userEntity = UserMapper.INSTANCE.toEntity(user);
        userRepository.save(userEntity);
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
