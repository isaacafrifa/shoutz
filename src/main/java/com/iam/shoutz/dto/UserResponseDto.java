package com.iam.shoutz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponseDto(
        Long userId,
        String username,
        String firstName,
        String lastName,
        String profilePictureUrl,
        LocalDate createdOn
) {
}
