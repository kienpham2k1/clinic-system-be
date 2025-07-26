package org.clinic.authservice.controller;

import org.clinic.common_security.security.enums.Permission;
import org.clinic.common_security.security.enums.Role;
import org.clinic.common_security.security.service.JwtPrivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private JwtPrivateService jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Map<String, Object> claims = new HashMap<>();

        // Giả lập: nếu user là admin
        if ("admin".equals(username) && "123456".equals(password)) {
            claims.put("username", username);
            claims.put("role", List.of(Role.ADMIN, Role.DOCTOR));
            claims.put("permission", List.of(Permission.ADMIN_READ));
        } else if ("user".equals(username) && "123456".equals(password)) {
            claims.put("username", username);
            claims.put("role", "USER");
        } else {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(claims, null);
        return Map.of("token", token);
    }
}
