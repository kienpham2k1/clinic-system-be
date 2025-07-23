package org.clinic.commonserviceweb.commonSecurity.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "jwt.public")
public class JwtPublicProperties {
    private String publicKeyPath;
}