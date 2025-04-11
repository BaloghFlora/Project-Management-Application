package com.proj_mngmt.proj_mngmt.exception.model;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.Map;

@Builder
public record ExceptionBody(
        ZonedDateTime timestamp,
        String code,
        String message,
        Map<String, String> details
) { }