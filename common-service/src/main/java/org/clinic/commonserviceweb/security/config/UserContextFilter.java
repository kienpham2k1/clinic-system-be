package org.clinic.commonserviceweb.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.clinic.commonserviceweb.security.dto.UserContext;
import org.clinic.commonserviceweb.security.enums.Permission;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String userId = request.getHeader("X-USER-ID");
        String username = request.getHeader("X-USERNAME");
        String role = request.getHeader("X-ROLE");
        String perms = request.getHeader("X-PERMISSIONS");

        Set<Permission> permissions = Arrays.stream(Optional.ofNullable(perms).orElse("")
                        .split(","))
                .filter(s -> !s.isEmpty())
                .map(String::trim)
                .map(Permission::valueOf)
                .collect(Collectors.toSet());

        if (userId != null) {
            UserContext context = new UserContext(userId, username, role, permissions);
            UserContextHolder.setContext(context);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            UserContextHolder.clear();
        }
    }
}
