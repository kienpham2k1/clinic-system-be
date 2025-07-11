package org.example.apigateway.config;

import io.jsonwebtoken.Claims;
import org.example.commonservice.commonSecurity.utils.JwtPublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {
    @Autowired
    private JwtPublicUtil jwtPublicUtil;

    @Bean("ipKeyResolver")
    @Primary
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(
                exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
        );
    }

    @Bean("routeKeyResolver")
    public KeyResolver routeKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().toString());
    }

    @Bean("userHeaderKeyResolver")
    public KeyResolver userHeaderKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getHeaders().getFirst("X-User-Id"));
    }

    @Bean("userKeyResolver")
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (token != null && token.startsWith("Bearer ")) {
                Claims claims = jwtPublicUtil.validateToken(token.substring(7));
                return Mono.just(claims.get("username", String.class));
            }
            return Mono.just("anonymous");
        };
    }
}