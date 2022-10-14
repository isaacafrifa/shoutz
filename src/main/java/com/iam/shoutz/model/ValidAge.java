package com.iam.shoutz.model;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
This is customized constraint annotation to validate that a provided
date compared to the present date is above than 18yrs"
 */
@Constraint(validatedBy = ValidAgeValidator.class)
@Target({ElementType.FIELD, CONSTRUCTOR })
@Retention(RUNTIME)
@Documented
public @interface ValidAge {

    String message() default "User must be 18 years and above";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
