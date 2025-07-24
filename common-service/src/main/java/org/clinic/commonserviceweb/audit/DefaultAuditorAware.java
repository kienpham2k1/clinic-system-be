package org.clinic.commonserviceweb.audit;

import org.clinic.commonserviceweb.security.service.UserContextProvider;
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