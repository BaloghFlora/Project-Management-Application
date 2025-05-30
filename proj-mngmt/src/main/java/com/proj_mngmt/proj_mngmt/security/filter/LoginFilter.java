package com.proj_mngmt.proj_mngmt.security.filter;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proj_mngmt.proj_mngmt.exception.model.ExceptionBody;
import com.proj_mngmt.proj_mngmt.exception.model.ExceptionCode;
import com.proj_mngmt.proj_mngmt.model.dto.auth.LoginRequestDTO;
import com.proj_mngmt.proj_mngmt.security.util.SecurityConstants;
import com.proj_mngmt.proj_mngmt.security.util.SecurityProperties;
import com.proj_mngmt.proj_mngmt.security.util.SecurityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final SecurityProperties securityProperties;
    private final ObjectMapper objectMapper;

    public LoginFilter(
            AuthenticationManager authenticationManager,
            SecurityProperties securityProperties,
            ObjectMapper objectMapper
    ) {
        super(authenticationManager);
        setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
        this.securityProperties = securityProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        // Skip processing for OPTIONS requests - they're handled by the CORS filter
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
            return null;
        }

        // Check if request has content
        if (request.getContentLength() == 0) {
            log.warn("Received empty request body for login attempt");
            throw new BadCredentialsException("Request body is empty");
        }

        try {
            LoginRequestDTO authenticationRequest = objectMapper.readValue(
                    request.getInputStream(),
                    LoginRequestDTO.class
            );

            // Validate that required fields are present
            if (authenticationRequest.email() == null || authenticationRequest.email().trim().isEmpty()) {
                throw new BadCredentialsException("Email is required");
            }

            if (authenticationRequest.password() == null || authenticationRequest.password().trim().isEmpty()) {
                throw new BadCredentialsException("Password is required");
            }

            log.info("Login attempt for user: {}", authenticationRequest.email());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.email(),
                    authenticationRequest.password()
            );

            return getAuthenticationManager().authenticate(authentication);
        } catch (IOException e) {
            log.error("Failed to parse login request: {}", e.getMessage());
            throw new BadCredentialsException("Invalid request format: " + e.getMessage());
        } catch (Exception e) {
            log.error("Authentication error: {}", e.getMessage());
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) {
        String accessToken = SecurityUtil.generateJwtToken(
                ((User) authResult.getPrincipal()).getUsername(),
                authResult.getAuthorities(),
                securityProperties.getTokenExpirationDays(),
                securityProperties.getSecretKey()
        );

        response.addCookie(SecurityUtil.buildCookie(
                SecurityConstants.JWT_TOKEN,
                accessToken,
                securityProperties.getTokenExpirationDays()
        ));

        // Add CORS headers
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");

        response.setStatus(HttpStatus.OK.value());
        log.info("Login successful for user: {}", ((User) authResult.getPrincipal()).getUsername());
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // Add CORS headers
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");

        log.warn("Login failed: {}", failed.getMessage());
        objectMapper.writeValue(response.getWriter(), getExceptionBody(failed.getMessage()));
    }

    private ExceptionBody getExceptionBody(String message) {
        return ExceptionBody.builder()
                .timestamp(ZonedDateTime.now())
                .message(message)
                .code(ExceptionCode.INVALID_CREDENTIALS.getCode())
                .build();
    }
}