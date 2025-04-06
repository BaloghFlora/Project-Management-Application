package com.project_management_backend.Project.Management.model.dto.project;

public record ProjectResponseDTO(
        Integer id,
        String projectName,
        String description,
        Integer teamId,
        String teamName
) { }