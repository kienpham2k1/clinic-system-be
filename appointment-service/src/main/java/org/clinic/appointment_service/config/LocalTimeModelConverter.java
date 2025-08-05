package org.clinic.appointment_service.config;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Iterator;

@Component
public class LocalTimeModelConverter implements ModelConverter {
    @Override
    public Schema<?> resolve(
            AnnotatedType type,
            ModelConverterContext context,
            Iterator<ModelConverter> chain) {

        if (type.getType() == LocalTime.class) {
            return new StringSchema()
                    .format("time")
                    .example("00:00:00");
        }

        if (chain.hasNext()) {
            return chain.next().resolve(type, context, chain);
        }

        return null;
    }
}
