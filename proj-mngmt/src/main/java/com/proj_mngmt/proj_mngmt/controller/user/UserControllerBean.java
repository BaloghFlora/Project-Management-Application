package com.proj_mngmt.proj_mngmt.controller.user;

import com.proj_mngmt.proj_mngmt.model.dto.user.UserResponseDTO;
import com.proj_mngmt.proj_mngmt.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserControllerBean implements UserController {

    private final UserService userService;

    @Override
    public UserResponseDTO getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("Getting user info for: {}", email);

        return userService.findByEmail(email);
    }

    @Override
    public UserResponseDTO findById(Integer id) {
        return userService.findById(id);
    }
}