package org.clinic.common_service_web;

import org.clinic.common_service_web.security.config.JwtPrivateProperties;
import org.clinic.common_service_web.security.config.JwtPublicProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JwtPrivateProperties.class, JwtPublicProperties.class})
@ComponentScan(basePackages = {"org.clinic.common_service_web.security",
        "org.clinic.common_service_web.context"})
public class AutoConfiguration {
}