package org.example.apigateway.config;

import io.jsonwebtoken.Claims;
import org.example.apigateway.exception.JwtAuthException;
import org.example.commonservice.utils.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private static final Map<String, List<String>> PERMISSIONS = Map.of(
            "/patients/delete", List.of("ADMIN"),
            "/patients", List.of("ADMIN", "USER")
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.contains("/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
            throw new JwtAuthException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        try {
            Claims claims = JwtUtil.validateToken(token);
            String role = claims.get("role", String.class);
            String username = claims.get("username", String.class);

            for (Map.Entry<String, List<String>> entry : PERMISSIONS.entrySet()) {
                if (path.startsWith(entry.getKey()) && !entry.getValue().contains(role)) {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
            }

            exchange = exchange.mutate()
                    .request(r -> r.headers(headers -> {
                        headers.set("X-User", username);
                        headers.set("X-Role", role);
                    }))
                    .build();

        } catch (Exception e) {
            throw new JwtAuthException("Invalid JWT token: " + e.getMessage());
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
