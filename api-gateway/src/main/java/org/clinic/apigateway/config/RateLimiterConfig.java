package org.clinic.apigateway.config;

import org.clinic.commonserviceweb.security.utils.JwtPublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.Map;

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
                Map<String, Object> claims = jwtPublicUtil.validateToken(token.substring(7));
                return Mono.just(claims.get("username").toString());
            }
            return Mono.just("anonymous");
        };
    }
}