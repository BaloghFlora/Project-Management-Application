package com.proj_mngmt.proj_mngmt.model.dto.auth;

import com.proj_mngmt.proj_mngmt.model.entity.Role;

public record RegistrationResponseDTO(
        Integer id,
        String name,
        String email,
        Role role,
        String message
) { }