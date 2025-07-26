package org.clinic.commonserviceweb.security.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.clinic.commonserviceweb.exception.AccessDeniedException;
import org.clinic.commonserviceweb.exception.dto.error.ErrorResponseEntity;
import org.clinic.commonserviceweb.security.dto.RequestPermissions;
import org.clinic.commonserviceweb.security.dto.UserContext;
import org.clinic.commonserviceweb.security.enums.Permission;
import org.clinic.commonserviceweb.security.enums.Role;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
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
        try {
            securityChain(request, response, filterChain);
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException ex) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write(convertErrorToJson(ex, HttpStatus.FORBIDDEN, request.getRequestURI()));
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write(convertErrorToJson(ex, HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI()));
        } finally {
            UserContextHolder.clear();
        }
    }

    private String convertErrorToJson(Exception ex, HttpStatus httpStatus, String uri) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(ex.getMessage())
                .path(uri)
                .timestamp(LocalDateTime.now())
                .build();
        return mapper.writeValueAsString(errorResponse);
    }

    protected void securityChain(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
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
                        throw new AccessDeniedException("Access denied: No permission config for path " + path);
                    }
                });

        UserContext context = new UserContext(userId, username, roles, permissions);
        UserContextHolder.setContext(context);
    }
}
