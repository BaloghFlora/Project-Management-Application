package com.proj_mngmt.proj_mngmt.model.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequestDTO(
        @NotBlank(message = "Task name is required and cannot be empty.")
        @Size(min = 2, max = 50, message = "Task name must be between 2 and 50 characters.")
        String taskName,

        @Size(max = 255, message = "Description cannot exceed 255 characters.")
        String description,

        @NotBlank(message = "Status is required.")
        String status,

        Integer projectId,

        Integer assignedUserId
) { }