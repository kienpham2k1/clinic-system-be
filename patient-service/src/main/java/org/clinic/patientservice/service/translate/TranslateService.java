package org.clinic.patientservice.service.translate;

import jakarta.annotation.Nullable;

import java.util.Locale;

public interface TranslateService {
    String translate(String key, Locale locale);

    String translate(String key, @Nullable Object[] args);
}
