package org.clinic.patientservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RequiredFieldValidator implements ConstraintValidator<RequiredField, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value != null; // giống như NotNull
    }
}
