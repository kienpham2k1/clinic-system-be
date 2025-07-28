package org.clinic.authservice.controller;

import org.clinic.authservice.dto.request.LoginRequest;
import org.clinic.authservice.dto.response.AuthorizeResponse;
import org.clinic.authservice.dto.response.RoleResponse;
import org.clinic.authservice.dto.response.UserResponse;
import org.clinic.authservice.service.UserService;
import org.clinic.common_security.security.enums.Role;
import org.clinic.common_security.security.service.JwtPrivateService;
import org.clinic.commonserviceweb.exception.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private JwtPrivateService jwtUtil;

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {

        UserResponse userResponse = userService.getUserByUsername(loginRequest.getUsername());
        boolean isAuthenticate = passwordEncoder.matches(loginRequest.getPassword(), userResponse.getPassword());

        if (!isAuthenticate) throw new AccessDeniedException("Invalid username or password");

        List<RoleResponse> roles = userResponse.getAuthorizes().stream().map(AuthorizeResponse::getRole).collect(Collectors.toList());
        List<Role> listRoleName = Optional.of(roles).orElse(Collections.emptyList()).stream().map(RoleResponse::getName).toList();

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userResponse.getId());
        claims.put("username", userResponse.getUsername());
        claims.put("role", listRoleName);

        String token = jwtUtil.generateToken(claims, null);
        return Map.of("token", token, "refreshToken", token);
    }

    @PostMapping("/logout")
    public void logout() {

    }
}
