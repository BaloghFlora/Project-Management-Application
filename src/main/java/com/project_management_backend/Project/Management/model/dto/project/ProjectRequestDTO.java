package com.project_management_backend.Project.Management.model.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectRequestDTO(
        @NotBlank(message = "Project name is required and cannot be empty.")
        @Size(min = 2, max = 50, message = "Project name must be between 2 and 50 characters.")
        String projectName,

        @Size(max = 255, message = "Description cannot exceed 255 characters.")
        String description,

        @NotNull(message = "Team ID is required.")
        Integer teamId
) { }