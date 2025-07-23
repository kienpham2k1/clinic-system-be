package org.clinic.apigateway.config;

import org.clinic.apigateway.exception.JwtAuthException;
import org.clinic.commonserviceweb.security.utils.JwtPublicUtil;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtAuthGatewayFilterFactory.Config> {
    @Value("${jwt.ttl:300000}")
    private Long ttlJwtTokenRedis;
    private final JwtPublicUtil jwtPublicUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    public JwtAuthGatewayFilterFactory(JwtPublicUtil jwtPublicUtil, RedisTemplate<String, Object> redisTemplate) {
        super(Config.class);
        this.jwtPublicUtil = jwtPublicUtil;
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
                String role;
                String userId;
                Map<String, Object> claims = (Map<String, Object>) redisTemplate.opsForValue().get(redisKey);
                if (claims == null) {
                    claims = jwtPublicUtil.validateToken(token);
                    redisTemplate.opsForValue().set(redisKey, claims, Duration.ofMillis(ttlJwtTokenRedis));
                    userId = "kienpt32";
                    MDC.put("userid", userId);
                }
                role = claims.get("role").toString();

                if (!config.isRoleAllowed(method, role)) {
                    throw new JwtAuthException("Access denied for " + method + " " + path + " [" + role + "]");
                }

                return chain.filter(exchange);
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

        public void setGET(List<String> roles) {
            methodRoles.put("GET", roles);
        }

        public void setPOST(List<String> roles) {
            methodRoles.put("POST", roles);
        }

        public void setPUT(List<String> roles) {
            methodRoles.put("PUT", roles);
        }

        public void setDELETE(List<String> roles) {
            methodRoles.put("DELETE", roles);
        }

        public List<String> getAllowedRoles(HttpMethod method) {
            return methodRoles.get(method.name());
        }

        public boolean isRoleAllowed(HttpMethod method, String role) {
            List<String> allowedRoles = getAllowedRoles(method);
            return allowedRoles != null && allowedRoles.contains(role);
        }
    }
}