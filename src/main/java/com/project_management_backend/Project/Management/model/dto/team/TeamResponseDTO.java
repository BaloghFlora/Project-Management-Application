package com.project_management_backend.Project.Management.model.dto.team;

public record TeamResponseDTO(
        Integer id,
        String teamName,
        String description
) { }