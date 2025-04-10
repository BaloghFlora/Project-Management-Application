package com.project_management_backend.Project.Management.security.service.user;

import com.project_management_backend.Project.Management.exception.model.ExceptionCode;
import com.project_management_backend.Project.Management.model.entity.UserEntity;
import com.project_management_backend.Project.Management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceBean implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(this::getUserDetails)
                .orElseThrow(() -> new BadCredentialsException(ExceptionCode.INVALID_CREDENTIALS.getMessage()));
    }

    private UserDetails getUserDetails(UserEntity user) {
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(String.valueOf(user.getRole()))
                .build();
    }
}