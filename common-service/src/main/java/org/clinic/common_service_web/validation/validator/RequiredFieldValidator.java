package org.clinic.common_service_web.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.clinic.common_service_web.validation.annotation.RequiredField;

public class RequiredFieldValidator implements ConstraintValidator<RequiredField, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value != null; // giống như NotNull
    }
}
