package com.proj_mngmt.proj_mngmt.model.dto.project;

public record ProjectResponseDTO(
        Integer id,
        String projectName,
        String description,
        Integer teamId,
        String teamName
) { }