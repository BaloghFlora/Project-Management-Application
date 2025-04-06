package com.project_management_backend.Project.Management.model.dto.team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TeamRequestDTO(
        @NotBlank(message = "Team name is required and cannot be empty.")
        @Size(min = 2, max = 50, message = "Team name must be between 2 and 50 characters.")
        String teamName,

        @Size(max = 255, message = "Description cannot exceed 255 characters.")
        String description
) { }