package org.clinic.common_service_web.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.clinic.common_service_web.validation.validator.RequiredFieldValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RequiredFieldValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredField {
    String message() default "This field is required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
