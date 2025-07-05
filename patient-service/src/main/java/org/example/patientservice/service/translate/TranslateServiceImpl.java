package org.example.patientservice.service.translate;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TranslateServiceImpl implements TranslateService {

    private final MessageSource messageSource;

    public TranslateServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String translate(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }

    @Override
    public String translate(String key, @Nullable Object[] args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }
}