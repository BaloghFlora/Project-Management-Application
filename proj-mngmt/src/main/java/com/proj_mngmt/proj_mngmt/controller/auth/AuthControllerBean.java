package com.proj_mngmt.proj_mngmt.controller.auth;

import com.proj_mngmt.proj_mngmt.model.dto.auth.LoginRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthControllerBean implements AuthController {

    @Override
    public void login(LoginRequestDTO loginRequestDTO) {
        log.info("[AUTH] User {} logged in", loginRequestDTO.email());
    }
}
