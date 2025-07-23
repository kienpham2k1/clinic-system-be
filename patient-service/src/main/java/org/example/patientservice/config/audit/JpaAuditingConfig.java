package org.example.patientservice.config.audit;

import org.example.commonservice.context.UserContextProvider;
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