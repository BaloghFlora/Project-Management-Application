package com.project_management_backend.Project.Management.model.dto.user;

import com.project_management_backend.Project.Management.model.entity.Role;

public record UserResponseDTO(
        Integer id,
        String name,
        String email,
        Role role,
        Integer teamId,
        String teamName
) { }