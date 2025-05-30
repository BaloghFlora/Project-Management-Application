package com.proj_mngmt.proj_mngmt.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequestDTO(
        @NotBlank(message = "Name is required and cannot be empty.")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters.")
        String name,

        @NotBlank(message = "Email is required and cannot be empty.")
        @Email(message = "Email should be valid.")
        String email,

        @NotBlank(message = "Password is required and cannot be empty.")
        @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
        String password,

        @NotBlank(message = "Confirm password is required and cannot be empty.")
        String confirmPassword
) { }