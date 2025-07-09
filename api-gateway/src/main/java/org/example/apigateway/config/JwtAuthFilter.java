//package org.example.apigateway.config;
//
//import io.jsonwebtoken.Claims;
//import org.example.apigateway.exception.JwtAuthException;
//import org.example.commonservice.utils.JwtUtil;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.server.PathContainer;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.util.pattern.PathPattern;
//import org.springframework.web.util.pattern.PathPatternParser;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class JwtAuthFilter implements GlobalFilter, Ordered {
//    private static final PathPatternParser patternParser = new PathPatternParser();
//
//    private static final Map<PathPattern, List<HttpMethod>> WHITE_LIST = Map.of(
//            patternParser.parse("/api/v1/auth/**"), List.of(HttpMethod.POST)
//    );
//
//    private static final Map<PathPattern, Map<HttpMethod, List<String>>> PERMISSIONS = Map.of(
//            patternParser.parse("/api/v1/patients/**"), Map.of(
//                    HttpMethod.GET, List.of("ADMIN", "USER"),
//                    HttpMethod.POST, List.of("ADMIN")
//            ),
//            patternParser.parse("/api/v1/appointments/**"), Map.of(
//                    HttpMethod.GET, List.of("DOCTOR", "ADMIN"),
//                    HttpMethod.POST, List.of("DOCTOR")
//            )
//    );
//
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String path = exchange.getRequest().getURI().getPath();
//        HttpMethod method = exchange.getRequest().getMethod();
//
//        if (WHITE_LIST.entrySet().stream()
//                .anyMatch(entry -> entry.getKey().matches(PathContainer.parsePath(path))
//                        && entry.getValue().contains(method))) {
//            return chain.filter(exchange);
//        }
//
//        boolean matchedPermission = PERMISSIONS.entrySet().stream()
//                .anyMatch(entry -> entry.getKey().matches(PathContainer.parsePath(path))
//                );
//
//        if (!matchedPermission) {
//            throw new JwtAuthException("Access denied: No permission config for path " + path);
//        }
//        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new JwtAuthException("Missing or invalid Authorization header");
//        }
//
//        String token = authHeader.substring(7);
//        try {
//            Claims claims = JwtUtil.validateToken(token);
//            String role = claims.get("role", String.class);
//            String username = claims.get("username", String.class);
//
//            PERMISSIONS.entrySet().stream()
//                    .filter(entry -> entry.getKey().matches(PathContainer.parsePath(path))
//                    )
//                    .findFirst()
//                    .ifPresent(entry -> {
//                        Map<HttpMethod, List<String>> methodRoleMap = entry.getValue();
//                        List<String> allowedRoles = methodRoleMap.get(method);
//                        if (allowedRoles == null || !allowedRoles.contains(role)) {
//                            throw new JwtAuthException("Access denied for " + method + " " + path + " [" + role + "]");
//                        }
//                    });
//
//            exchange = exchange.mutate()
//                    .request(r -> r.headers(headers -> {
//                        headers.set("X-User", username);
//                        headers.set("X-Role", role);
//                    }))
//                    .build();
//
//        } catch (Exception e) {
//            throw new JwtAuthException("Invalid JWT token: " + e.getMessage());
//        }
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return -1;
//    }
//}
