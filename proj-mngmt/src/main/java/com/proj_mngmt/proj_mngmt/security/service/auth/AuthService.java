package com.proj_mngmt.proj_mngmt.security.service.auth;

import java.util.UUID;

public interface AuthService {

    boolean isSelf(UUID userId);
}
