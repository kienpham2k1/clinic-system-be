package org.clinic.commonserviceweb.commonAudit.entity.audit;

import org.clinic.commonserviceweb.context.UserContextProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class DefaultAuditorAware implements AuditorAware<String> {
    private final UserContextProvider provider;

    public DefaultAuditorAware(UserContextProvider provider) {
        this.provider = provider;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(provider.getUserId()).or(() -> Optional.of("anonymous"));
    }
}