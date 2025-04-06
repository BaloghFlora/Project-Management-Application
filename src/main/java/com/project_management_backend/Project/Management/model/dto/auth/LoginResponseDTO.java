package com.project_management_backend.Project.Management.model.dto.auth;

import java.util.UUID;

import com.project_management_backend.Project.Management.model.entity.Role;

public record LoginResponseDTO(UUID id, String email, Role role) { }