package com.proj_mngmt.proj_mngmt.security.service.auth;

import com.proj_mngmt.proj_mngmt.exception.model.ExceptionCode;
import com.proj_mngmt.proj_mngmt.model.entity.UserEntity;
import com.proj_mngmt.proj_mngmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
