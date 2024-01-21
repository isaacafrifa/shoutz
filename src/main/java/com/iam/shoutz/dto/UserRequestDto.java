package com.iam.shoutz.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @NotBlank(message = "Username cannot be empty")
        @Size(min = 5, message = "Username must be more than 5 letters")
        String username,
        @NotBlank(message = "First name cannot be empty")
        @Size(min = 2, message = "First name must be more than 2 letters")
        String firstName,
        @NotBlank(message = "Surname cannot be empty")
        @Size(min = 2, message = "Surname must be more than 2 letters")
        String lastName,
        String profilePictureUrl,
        @Max(value = 99, message = "Age should not be greater than 99")
        @Min(value = 18, message = "Age should be greater than 18")
        int age
) {
}
