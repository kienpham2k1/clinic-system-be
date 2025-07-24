package org.clinic.commonserviceweb;

import org.clinic.commonserviceweb.security.jwt.JwtPrivateProperties;
import org.clinic.commonserviceweb.security.jwt.JwtPublicProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JwtPrivateProperties.class, JwtPublicProperties.class})
@ComponentScan(basePackages = {"org.clinic.commonserviceweb"
})
public class AutoConfiguration {
}