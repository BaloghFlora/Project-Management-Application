package com.proj_mngmt.proj_mngmt.model.dto.task;

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