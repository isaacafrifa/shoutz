package com.iam.shoutz.util;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidAgeValidatorTest {

    private ConstraintValidatorContext constraintValidatorContext;
    private ValidAgeValidator validAgeValidator;
    private LocalDate birthDate;

    @BeforeEach
    void setup() {
        validAgeValidator = new ValidAgeValidator();
    }

    @Test
    public void itShouldTestCalculateYoungerAge() {
        // given
        birthDate = LocalDate.of(2010, 5, 17);
        // when
        var actual = validAgeValidator.isValid(birthDate, constraintValidatorContext);
        // that
        assertFalse(actual);
    }

    /* Use BES techniques- Boundary, Equivalence and Special cases */
    @Nested
    @DisplayName("Testing edge cases of age validator")
    class BoundaryTests {

        @Test
        @DisplayName("Testing for exactly 18 years")
        public void itShouldTestCalculateAge_Exactly18() {
            // given
            birthDate = LocalDate.now().minusYears(18);
            // when
            var actual = validAgeValidator.isValid(birthDate, constraintValidatorContext);
            // that
            assertTrue(actual);
        }

        @Test
        @DisplayName("Testing for 1 day short of 18 years")
        public void itShouldTestCalculateAge_Boundary1() {
            // given
            birthDate = LocalDate.now().minusYears(18).plusDays(1);
            // when
            var actual = validAgeValidator.isValid(birthDate, constraintValidatorContext);
            // that
            assertFalse(actual);
        }

        @Test
        @DisplayName("Testing for 1 day older than 18 years")
        public void itShouldTestCalculateAge_Boundary2() {
            // given
            birthDate = LocalDate.now().minusYears(18).minusDays(1);
            // when
            var actual = validAgeValidator.isValid(birthDate, constraintValidatorContext);
            // that
            assertTrue(actual);
        }
    }

    @Test
    public void itShouldTestCalculateOlderAge() {
        // given
        birthDate = LocalDate.of(1961, 5, 17);
        // when
        var actual = validAgeValidator.isValid(birthDate, constraintValidatorContext);
        // that
        assertTrue(actual);
    }

    @Test
    @DisplayName("Testing for when another type other than LocalDate is used")
    public void itShouldTestWhenInput_IsNotOfType_LocalDate() {
        // given
        String birthDate = "1961, 5, 17";
        assertThrows(IllegalArgumentException.class, () -> {
            validAgeValidator.isValid(birthDate, constraintValidatorContext);
        }, "Should throw illegal argument exception");
    }
}