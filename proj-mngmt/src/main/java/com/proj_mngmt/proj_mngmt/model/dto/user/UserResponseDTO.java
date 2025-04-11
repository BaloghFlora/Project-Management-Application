package com.proj_mngmt.proj_mngmt.model.dto.user;

import com.proj_mngmt.proj_mngmt.model.entity.Role;

public record UserResponseDTO(
        Integer id,
        String name,
        String email,
        Role role,
        Integer teamId,
        String teamName
) { }