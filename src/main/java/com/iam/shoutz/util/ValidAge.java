package com.iam.shoutz.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
This is customized constraint annotation to validate that the provided
date compared to the present date is equal or above 18yrs
 */
@Constraint(validatedBy = ValidAgeValidator.class)
@Target({ElementType.FIELD })
@Retention(RUNTIME)
@Documented
public @interface ValidAge {

    String message() default "User must be 18 years and above";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
