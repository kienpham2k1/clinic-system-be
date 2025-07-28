package org.clinic.common_service_web.localeTimeZone.config.context;

import org.clinic.common_service_web.localeTimeZone.LocaleTimeZoneContext;

public class TimeZoneContextHolder {
    private static ThreadLocal<LocaleTimeZoneContext> timeZoneContextHolder = new ThreadLocal<LocaleTimeZoneContext>();

    public static LocaleTimeZoneContext getTimeZone() {
        return timeZoneContextHolder.get();
    }

    public static void setTimeZone(LocaleTimeZoneContext timeZoneContext) {
        timeZoneContextHolder.set(timeZoneContext);
    }

    public static void clear() {
        timeZoneContextHolder.remove();
    }

}
