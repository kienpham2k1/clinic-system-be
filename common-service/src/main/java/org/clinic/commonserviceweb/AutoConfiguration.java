package org.clinic.commonserviceweb;

import org.clinic.commonserviceweb.config.jwt.JwtPrivateProperties;
import org.clinic.commonserviceweb.config.jwt.JwtPublicProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JwtPrivateProperties.class, JwtPublicProperties.class})
@ComponentScan(basePackages = {"org.clinic.commonserviceweb.utils",
        "org.clinic.commonserviceweb.service",
        "org.clinic.commonserviceweb.wrapper",
        "org.clinic.commonserviceweb.config.advice"})
public class AutoConfiguration {
}