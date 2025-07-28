package org.clinic.api_gateway.config;

import org.clinic.api_gateway.exception.JwtAuthException;
import org.clinic.common_security.security.enums.Permission;
import org.clinic.common_security.security.enums.Role;
import org.clinic.common_security.security.service.JwtPublicService;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtAuthGatewayFilterFactory.Config> {
    private final JwtPublicService jwtPublicService;
    private final RedisTemplate<String, Object> redisTemplate;
    @Value("${jwt.ttl:300000}")
    private Long ttlJwtTokenRedis;

    public JwtAuthGatewayFilterFactory(JwtPublicService jwtPublicService, RedisTemplate<String, Object> redisTemplate) {
        super(Config.class);
        this.jwtPublicService = jwtPublicService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            HttpMethod method = exchange.getRequest().getMethod();
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            // Check token
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                MDC.put("userid", "anonymous");
                throw new JwtAuthException("Missing or invalid Authorization header");
            }

            try {
                String token = authHeader.substring(7);
                String redisKey = "auth:token" + token;
                String userId;
                String username = "anonymous";
                boolean accessDenied = true;
                Map<String, Object> claims = (Map<String, Object>) redisTemplate.opsForValue().get(redisKey);
                if (claims == null) {
                    claims = jwtPublicService.parseClaimsFromToken(token);
                    redisTemplate.opsForValue().set(redisKey, claims, Duration.ofMillis(ttlJwtTokenRedis));

                    userId = claims.get("userId") != null ? claims.get("userId").toString() : null;
                    MDC.put("userid", userId);
                } else {
                    userId = "anonymous";
                }

                Set<Role> roles = jwtPublicService.getRoles(claims);
                Set<Permission> permissions = roles.stream().map(r -> Permission.valueOf(r.name())).collect(Collectors.toSet());

                if (!roles.isEmpty()) {
                    accessDenied = config.isRoleAllowed(roles);
                }
                if (!permissions.isEmpty()) {
                    accessDenied = accessDenied ? config.isPermissionAllowed(permissions, method) : accessDenied;
                }
                if (accessDenied) {
                    throw new JwtAuthException("Access denied for " + method + " " + path);
                }

                ServerHttpRequest request = exchange.getRequest()
                        .mutate()
                        .headers(httpHeaders -> {
                            httpHeaders.set("X-USER-ID", userId);
                            httpHeaders.set("X-USERNAME", username);
                            httpHeaders.set("X-ROLE", roles.stream()
                                    .map(Enum::name) // chuyển enum về String
                                    .collect(Collectors.joining(",")));
                            httpHeaders.set("X-PERMISSIONS", permissions.stream()
                                    .map(Enum::name) // chuyển enum về String
                                    .collect(Collectors.joining(",")));
                        })
                        .build();

                ServerWebExchange mutatedExchange = exchange
                        .mutate()
                        .request(request)
                        .build();
                return chain.filter(mutatedExchange);
            } catch (Exception e) {
                throw new JwtAuthException("Invalid JWT token: " + e.getMessage());
            }
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of();
    }

    public static class Config {
        private final Map<String, List<String>> methodRoles = new HashMap<>();

        public void setROLE(List<String> roles) {
            methodRoles.put("ROLE", roles);
        }

        public void setGET(List<String> permissions) {
            methodRoles.put("GET", permissions);
        }

        public void setPOST(List<String> permissions) {
            methodRoles.put("POST", permissions);
        }

        public void setPUT(List<String> permissions) {
            methodRoles.put("PUT", permissions);
        }

        public void setDELETE(List<String> permissions) {
            methodRoles.put("DELETE", permissions);
        }

        public List<String> getAllowedAuthorize(HttpMethod method) {
            return methodRoles.get(method.name());
        }

        public List<String> getAllowedRole() {
            return methodRoles.get("ROLE");
        }

        public boolean isPermissionAllowed(Set<Permission> permission, HttpMethod method) {
            Set<Permission> allowedPermissionSet = Optional.ofNullable(getAllowedAuthorize(method))
                    .orElse(Collections.emptyList()) // tránh null
                    .stream()
                    .map(String::toUpperCase)
                    .map(Permission::valueOf)
                    .collect(Collectors.toSet());

            return permission.stream().noneMatch(allowedPermissionSet::contains);
        }

        public boolean isRoleAllowed(Set<Role> roles) {
            Set<Role> allowedRoles = Optional.ofNullable(getAllowedRole())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(String::toUpperCase)
                    .map(Role::valueOf)
                    .collect(Collectors.toSet());

            return roles.stream().noneMatch(allowedRoles::contains);
        }
    }
}