package org.clinic.common_security;

import org.clinic.common_security.security.jwt.JwtPrivateProperties;
import org.clinic.common_security.security.jwt.JwtPublicProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JwtPrivateProperties.class, JwtPublicProperties.class})
@ComponentScan(basePackages = {"org.clinic.common_security.security"
})
public class AutoConfiguration {
}