package com.proj_mngmt.proj_mngmt.exception.handler;


import com.proj_mngmt.proj_mngmt.exception.model.ExceptionBody;
import com.proj_mngmt.proj_mngmt.exception.model.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RegistrationExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionBody> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Registration validation error: {}", ex.getMessage());

        ExceptionBody exceptionBody = ExceptionBody.builder()
                .timestamp(ZonedDateTime.now())
                .message(ex.getMessage())
                .code(getCodeForMessage(ex.getMessage()))
                .build();

        HttpStatus status = ex.getMessage().contains("already registered") ?
                HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(exceptionBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionBody> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Validation error during registration: {}", ex.getMessage());

        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ExceptionBody exceptionBody = ExceptionBody.builder()
                .timestamp(ZonedDateTime.now())
                .message("Registration validation failed: " + errorMessage)
                .code(ExceptionCode.VALIDATION_ERROR.getCode())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionBody);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionBody> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("Data integrity violation during registration: {}", ex.getMessage());

        String message = "Email is already registered";
        if (ex.getMessage().contains("email")) {
            message = "Email is already registered";
        }

        ExceptionBody exceptionBody = ExceptionBody.builder()
                .timestamp(ZonedDateTime.now())
                .message(message)
                .code(ExceptionCode.EMAIL_TAKEN.getCode())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionBody);
    }

    private String getCodeForMessage(String message) {
        if (message.contains("already registered")) {
            return ExceptionCode.EMAIL_TAKEN.getCode();
        } else if (message.contains("password")) {
            return ExceptionCode.PASSWORD_MISMATCH.getCode();
        } else {
            return ExceptionCode.REGISTRATION_FAILED.getCode();
        }
    }
}
