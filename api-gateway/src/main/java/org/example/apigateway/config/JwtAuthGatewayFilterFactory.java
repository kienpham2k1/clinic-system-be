package org.example.apigateway.config;

import io.jsonwebtoken.Claims;
import org.example.apigateway.exception.JwtAuthException;
import org.example.commonservice.commonSecurity.utils.JwtPublicUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtAuthGatewayFilterFactory.Config> {
    private final JwtPublicUtil jwtPublicUtil;

    public JwtAuthGatewayFilterFactory(JwtPublicUtil jwtPublicUtil) {
        super(Config.class);
        this.jwtPublicUtil = jwtPublicUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            HttpMethod method = exchange.getRequest().getMethod();
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            // Check token
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new JwtAuthException("Missing or invalid Authorization header");
            }

            try {
                String token = authHeader.substring(7);
                Claims claims = jwtPublicUtil.validateToken(token);
                String role = claims.get("role", String.class);

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