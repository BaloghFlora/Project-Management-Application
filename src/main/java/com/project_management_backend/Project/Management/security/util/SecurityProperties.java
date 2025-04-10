package com.project_management_backend.Project.Management.security.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private String secretKey;
    private Integer tokenExpirationDays;
}
