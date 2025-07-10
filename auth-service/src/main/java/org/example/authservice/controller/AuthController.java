package org.example.authservice.controller;

import org.example.commonservice.commonSecurity.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Map<String, Object> claims = new HashMap<>();

        // Giả lập: nếu user là admin
        if ("admin".equals(username) && "123456".equals(password)) {
            claims.put("username", username);
            claims.put("role", "ADMIN");
        } else if ("user".equals(username) && "123456".equals(password)) {
            claims.put("username", username);
            claims.put("role", "USER");
        } else {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(claims);
        return Map.of("token", token);
    }
}
