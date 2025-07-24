package org.clinic.commonserviceweb.config.context;

import org.clinic.commonserviceweb.dto.LocaleTimeZoneContext;

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
