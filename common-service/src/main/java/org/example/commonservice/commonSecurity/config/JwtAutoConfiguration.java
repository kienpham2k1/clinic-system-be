package org.example.commonservice.commonSecurity.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JwtPrivateProperties.class, JwtPublicProperties.class})
@ComponentScan(basePackages = "org.example.commonservice.commonSecurity")
public class JwtAutoConfiguration {
}