package org.clinic.commonserviceweb.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.clinic.commonserviceweb.validation.annotation.RequiredField;

public class RequiredFieldValidator implements ConstraintValidator<RequiredField, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value != null; // giống như NotNull
    }
}
