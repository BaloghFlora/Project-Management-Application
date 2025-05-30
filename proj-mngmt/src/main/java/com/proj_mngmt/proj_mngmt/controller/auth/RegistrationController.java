package com.proj_mngmt.proj_mngmt.controller.auth;


import com.proj_mngmt.proj_mngmt.model.dto.auth.RegistrationRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.auth.RegistrationResponseDTO;
import com.proj_mngmt.proj_mngmt.exception.model.ExceptionBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "Operations for user authentication and registration")
public interface RegistrationController {

    @PostMapping("/register")
    @Operation(summary = "User registration", description = "Register a new user with TEAM_MEMBER role by default.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registration successful",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RegistrationResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid registration data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionBody.class))),
            @ApiResponse(responseCode = "409", description = "Email already registered",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionBody.class)))
    })
    @ResponseStatus(HttpStatus.CREATED)
    RegistrationResponseDTO register(@RequestBody @Valid RegistrationRequestDTO registrationRequestDTO);

    @GetMapping("/check-email")
    @Operation(summary = "Check email availability", description = "Check if an email is already registered.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email availability check completed"),
            @ApiResponse(responseCode = "400", description = "Invalid email format")
    })
    @ResponseStatus(HttpStatus.OK)
    boolean isEmailAvailable(@RequestParam String email);
}