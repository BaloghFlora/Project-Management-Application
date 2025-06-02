package com.proj_mngmt.proj_mngmt.model.dto.team;

import jakarta.validation.constraints.NotNull;

public record AddTeamMemberRequestDTO(
        @NotNull(message = "User ID is required.")
        Integer userId
) { }