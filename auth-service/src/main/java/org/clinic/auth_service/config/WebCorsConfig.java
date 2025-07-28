package org.clinic.auth_service.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/v3/api-docs")
                .allowedOrigins("http://localhost:8080") // hoặc "*"
                .allowedMethods("GET")
                .allowedHeaders("*");
    }
}