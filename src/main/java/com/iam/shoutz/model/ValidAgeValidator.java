package com.iam.shoutz.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

/*
This is customized constraint class to validate that a provided
date of birth compared to the present date is above than 18yrs"
 */
public class ValidAgeValidator implements ConstraintValidator<ValidAge, Object> {
    private static final int VALID_AGE=18;

    @Override
    public boolean isValid(Object providedDate, ConstraintValidatorContext constraintValidatorContext) {
        if (!(providedDate instanceof LocalDate)) {
            throw new IllegalArgumentException("Illegal method signature, "
                    + "expected parameter of type LocalDate.");
        }
        return Period.between((LocalDate) providedDate, LocalDate.now())
                .getYears() >= VALID_AGE;
    }
}
