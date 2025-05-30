package com.proj_mngmt.proj_mngmt.service.user;

import com.proj_mngmt.proj_mngmt.model.dto.auth.RegistrationRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.auth.RegistrationResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.Role;
import com.proj_mngmt.proj_mngmt.model.entity.UserEntity;
import com.proj_mngmt.proj_mngmt.repository.UserRepository;
import com.proj_mngmt.proj_mngmt.service.user.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegistrationServiceBean implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public RegistrationResponseDTO registerUser(RegistrationRequestDTO registrationRequestDTO) {
        log.info("Starting user registration for email: {}", registrationRequestDTO.email());

        // Validate passwords match
        if (!registrationRequestDTO.password().equals(registrationRequestDTO.confirmPassword())) {
            log.error("Password confirmation mismatch for email: {}", registrationRequestDTO.email());
            throw new IllegalArgumentException("Password and confirm password do not match");
        }

        // Check if email is already registered
        if (isEmailAlreadyRegistered(registrationRequestDTO.email())) {
            log.error("Email already registered: {}", registrationRequestDTO.email());
            throw new IllegalArgumentException("Email is already registered");
        }

        // Create new user entity
        UserEntity newUser = new UserEntity();
        newUser.setName(registrationRequestDTO.name());
        newUser.setEmail(registrationRequestDTO.email());
        newUser.setPassword(passwordEncoder.encode(registrationRequestDTO.password()));
        newUser.setRole(Role.TEAM_MEMBER); // Default role for new registrations

        // Save user to database
        UserEntity savedUser = userRepository.save(newUser);

        log.info("User successfully registered with ID: {} and email: {}", savedUser.getId(), savedUser.getEmail());

        return new RegistrationResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                "User registered successfully"
        );
    }

    @Override
    public boolean isEmailAlreadyRegistered(String email) {
        log.debug("Checking if email is already registered: {}", email);
        return userRepository.existsByEmail(email);
    }
}