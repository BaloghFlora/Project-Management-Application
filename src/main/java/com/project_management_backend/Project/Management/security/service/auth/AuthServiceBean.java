package com.project_management_backend.Project.Management.security.service.auth;

import java.util.UUID;

import com.project_management_backend.Project.Management.exception.model.ExceptionCode;
import com.project_management_backend.Project.Management.model.entity.UserEntity;
import com.project_management_backend.Project.Management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("authService")
@RequiredArgsConstructor
public class AuthServiceBean implements AuthService {

    private final UserRepository userRepository;

    @Override
    public boolean isSelf(UUID userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new BadCredentialsException(ExceptionCode.FORBIDDEN_ACCESS.getMessage()));

        return user.getId().equals(userId);
    }
}
