package com.proj_mngmt.proj_mngmt.model.dto.team;

import com.proj_mngmt.proj_mngmt.model.entity.Role;

public record TeamMemberResponseDTO(
        Integer id,
        String name,
        String email,
        Role role,
        Integer teamId,
        String teamName
) { }