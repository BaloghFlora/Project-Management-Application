package com.proj_mngmt.proj_mngmt.controller.user;

import com.proj_mngmt.proj_mngmt.model.dto.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/users")
@Tag(name = "User Management", description = "Operations for managing users")
public interface UserController {

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get current user info")
    UserResponseDTO getCurrentUserInfo();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get user by ID")
    UserResponseDTO findById(@PathVariable Integer id);
}