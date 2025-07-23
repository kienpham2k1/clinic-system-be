package org.example.patientservice.config.audit;

import org.example.commonservice.commonAudit.entity.audit.DefaultAuditorAware;
import org.example.commonservice.commonAudit.entity.audit.UserContextProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {
    @Bean
    public AuditorAware<String> auditorAware(UserContextProvider provider) {
        return new DefaultAuditorAware(provider);
    }
}