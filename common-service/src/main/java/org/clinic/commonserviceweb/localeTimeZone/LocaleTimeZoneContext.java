package org.clinic.commonserviceweb.localeTimeZone;

import java.util.Locale;
import java.util.TimeZone;

public class LocaleTimeZoneContext {
    private final Locale locale;
    private final TimeZone timeZone;

    public LocaleTimeZoneContext(Locale locale, TimeZone timeZone) {
        this.locale = locale;
        this.timeZone = timeZone;
    }

    public Locale getLocale() {
        return locale;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }
}