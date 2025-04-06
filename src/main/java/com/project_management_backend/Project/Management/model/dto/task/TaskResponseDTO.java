package com.project_management_backend.Project.Management.model.dto.task;

public record TaskResponseDTO(
        Integer id,
        String taskName,
        String description,
        String status,
        Integer projectId,
        String projectName,
        Integer assignedUserId,
        String assignedUserName
) { }