package com.comercio.app.utils.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistByUsernameValidation.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistByUsername {
    String message() default "{Hay algun error en sus credenciales}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
