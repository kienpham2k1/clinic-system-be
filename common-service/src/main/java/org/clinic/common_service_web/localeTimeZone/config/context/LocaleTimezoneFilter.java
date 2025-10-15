package org.clinic.common_service_web.localeTimeZone.config.context;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.clinic.common_service_web.localeTimeZone.LocaleTimeZoneContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;

@Component
@Order(1)
public class LocaleTimezoneFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        // Language
        String langHeader = req.getHeader("X-LANGUAGE");
        Locale locale = Locale.forLanguageTag(langHeader != null ? langHeader : "en");

        // Timezone
        String tzHeader = req.getHeader("X-TIMEZONE");
        TimeZone tz = TimeZone.getTimeZone(tzHeader != null ? tzHeader : "UTC");

        LocaleTimeZoneContext ltzContext = new LocaleTimeZoneContext(locale, tz);
        TimeZoneContextHolder.setTimeZone(ltzContext);
        try {
            chain.doFilter(request, response);
        } finally {
            TimeZoneContextHolder.clear();
        }
    }
}
