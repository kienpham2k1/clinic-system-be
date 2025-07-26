package org.clinic.commonserviceweb.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.clinic.commonserviceweb.exception.NotFoundException;
import org.clinic.commonserviceweb.security.dto.RequestPermissions;
import org.clinic.commonserviceweb.security.dto.UserContext;
import org.clinic.commonserviceweb.security.enums.Permission;
import org.clinic.commonserviceweb.security.enums.Role;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserContextFilter extends OncePerRequestFilter {
    private static final PathPatternParser patternParser = new PathPatternParser();

    private static final Set<PathPattern> WHITE_LIST = Set.of(
            patternParser.parse("/api/v1/auth/**")
    );


    private static final Map<PathPattern, RequestPermissions> RULES = Map.of(
            patternParser.parse("/api/v1/patients/**"),
            new RequestPermissions(
                    Set.of(Role.PATIENT),
                    Map.of(HttpMethod.GET, Set.of(Permission.ADMIN_READ))
            )
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        if (WHITE_LIST.stream()
                .anyMatch(entry ->
                        entry.matches(PathContainer.parsePath(path)))) {
            filterChain.doFilter(request, response);
        }

        String userId = request.getHeader("X-USER-ID");
        String username = request.getHeader("X-USERNAME");
        String role = request.getHeader("X-ROLE");
        String perms = request.getHeader("X-PERMISSIONS");
        Set<Role> roles = Arrays.stream(Optional.ofNullable(role).orElse("")
                        .split(","))
                .filter(s -> !s.isEmpty())
                .map(String::trim)
                .map(Role::valueOf)
                .collect(Collectors.toSet());

        Set<Permission> permissions = Arrays.stream(Optional.ofNullable(perms).orElse("")
                        .split(","))
                .filter(s -> !s.isEmpty())
                .map(String::trim)
                .map(Permission::valueOf)
                .collect(Collectors.toSet());

        RULES.entrySet().stream()
                .filter(entry ->
                        entry.getKey().matches(PathContainer.parsePath(path)))
                .findFirst()
                .ifPresent(entry -> {
                    boolean accessDenied = true;
                    RequestPermissions requestPermissions = entry.getValue();
                    if (!roles.isEmpty()) {
                        Set<Role> allowedRoles = requestPermissions.getRoles();
                        accessDenied = roles.stream().noneMatch(allowedRoles::contains);
                    }
                    if (!permissions.isEmpty()) {
                        Map<HttpMethod, Set<Permission>> allowedPermission = requestPermissions.getAuthorities();
                        Set<Permission> allowedPermissionSet = allowedPermission.get(method);
                        accessDenied = permissions.stream().noneMatch(allowedPermissionSet::contains);
                    }
                    if (accessDenied) {
                        throw new NotFoundException("Access denied: No permission config for path " + path);
                    }
                });

        UserContext context = new UserContext(userId, username, roles, permissions);
        UserContextHolder.setContext(context);

        try {
            filterChain.doFilter(request, response);
        } finally {
            UserContextHolder.clear();
        }
    }
}
