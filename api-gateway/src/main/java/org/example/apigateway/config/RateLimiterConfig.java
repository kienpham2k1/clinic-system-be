package org.example.apigateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(
                exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
        );
    }

    @Bean
    public KeyResolver routeKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().toString());
    }

    @Bean
    public KeyResolver userHeaderKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getHeaders().getFirst("X-User-Id"));
    }
}