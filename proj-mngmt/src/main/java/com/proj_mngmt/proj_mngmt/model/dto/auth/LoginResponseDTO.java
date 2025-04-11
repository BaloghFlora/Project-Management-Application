package com.proj_mngmt.proj_mngmt.model.dto.auth;

import java.util.UUID;

public record LoginResponseDTO(UUID id, String email, int role) { }