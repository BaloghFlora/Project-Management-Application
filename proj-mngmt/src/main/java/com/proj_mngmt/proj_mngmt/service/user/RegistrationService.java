package com.proj_mngmt.proj_mngmt.service.user;

import com.proj_mngmt.proj_mngmt.model.dto.auth.RegistrationRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.auth.RegistrationResponseDTO;
import org.springframework.transaction.annotation.Transactional;

public interface RegistrationService {

    @Transactional
    RegistrationResponseDTO registerUser(RegistrationRequestDTO registrationRequestDTO);

    boolean isEmailAlreadyRegistered(String email);
}