package com.project_management_backend.Project.Management.model.dto.user;

import com.project_management_backend.Project.Management.controller.util.RestUtil;
import jakarta.validation.constraints.Min;

import java.time.ZonedDateTime;
import java.util.Objects;

public record UserFilterDTO(String name,
                            ZonedDateTime birthDate,

                            @Min(value = 0, message = "Page number must be at least 0.")
                            Integer pageNumber,

                            @Min(value = 1, message = "Page size must be at least 1.")
                            Integer pageSize
) {
    public UserFilterDTO {
        pageNumber = Objects.requireNonNullElse(pageNumber, RestUtil.DEFAULT_PAGE_NUMBER);
        pageSize = Objects.requireNonNullElse(pageSize, RestUtil.DEFAULT_PAGE_SIZE);
    }

}
