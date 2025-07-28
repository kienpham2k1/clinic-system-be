package org.clinic.patientservice.config.audit;

import org.clinic.common_service_web.security.service.UserContextProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorAware(UserContextProvider userContextProvider) {
        return () -> Optional.ofNullable(userContextProvider.getUserId());
    }
}