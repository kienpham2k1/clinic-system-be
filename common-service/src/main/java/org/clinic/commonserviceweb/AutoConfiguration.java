package org.clinic.commonserviceweb;

import org.clinic.commonserviceweb.security.jwt.JwtPrivateProperties;
import org.clinic.commonserviceweb.security.jwt.JwtPublicProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JwtPrivateProperties.class, JwtPublicProperties.class})
@ComponentScan(basePackages = {"org.clinic.commonserviceweb.utils",
        "org.clinic.commonserviceweb.service",
        "org.clinic.commonserviceweb.wrapper",
        "org.clinic.commonserviceweb.config.context",
        "org.clinic.commonserviceweb.config.advice",
        "org.clinic.commonserviceweb.config.messagge",
        "org.clinic.commonserviceweb.security.service",
        "org.clinic.commonserviceweb.security.config",
        "org.clinic.commonserviceweb.security.aspect",
        "org.clinic.commonserviceweb.security.anotation",
})
public class AutoConfiguration {
}