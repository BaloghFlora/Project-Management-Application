package com.proj_mngmt.proj_mngmt.controller.auth;


import com.proj_mngmt.proj_mngmt.model.dto.auth.RegistrationRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.auth.RegistrationResponseDTO;
import com.proj_mngmt.proj_mngmt.service.user.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class RegistrationControllerBean implements RegistrationController {

    private final RegistrationService registrationService;

    @Override
    public RegistrationResponseDTO register(RegistrationRequestDTO registrationRequestDTO) {
        log.info("Registration request received for email: {}", registrationRequestDTO.email());
        return registrationService.registerUser(registrationRequestDTO);
    }

    @Override
    public boolean isEmailAvailable(@RequestParam String email) {
        log.debug("Email availability check for: {}", email);
        return !registrationService.isEmailAlreadyRegistered(email);
    }
}