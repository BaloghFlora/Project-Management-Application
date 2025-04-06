package com.project_management_backend.Project.Management.model.dto.user;

import com.project_management_backend.Project.Management.model.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "Name is required and cannot be empty.")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters.")
        String name,

        @NotBlank(message = "Email is required and cannot be empty.")
        @Email(message = "Email must be valid.")
        String email,

        @NotBlank(message = "Password is required and cannot be empty.")
        @Size(min = 8, message = "Password must be at least 8 characters.")
        String password,

        @NotNull(message = "Role is required.")
        Role role,

        Integer teamId
) { }