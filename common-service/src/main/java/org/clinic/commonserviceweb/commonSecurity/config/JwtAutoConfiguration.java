package org.clinic.commonserviceweb.commonSecurity.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JwtPrivateProperties.class, JwtPublicProperties.class})
@ComponentScan(basePackages = {"org.clinic.commonserviceweb.commonSecurity",
        "org.clinic.commonserviceweb.context"})
public class JwtAutoConfiguration {
}