package com.project_management_backend.Project.Management.security.service.auth;

import java.util.UUID;

public interface AuthService {

    boolean isSelf(UUID userId);
}
