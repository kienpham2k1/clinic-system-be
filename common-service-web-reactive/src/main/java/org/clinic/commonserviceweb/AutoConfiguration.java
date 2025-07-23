package org.clinic.commonserviceweb;

import org.clinic.commonserviceweb.security.config.JwtPrivateProperties;
import org.clinic.commonserviceweb.security.config.JwtPublicProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JwtPrivateProperties.class, JwtPublicProperties.class})
@ComponentScan(basePackages = {"org.clinic.commonserviceweb.security",
        "org.clinic.commonserviceweb.context"})
public class AutoConfiguration {
}