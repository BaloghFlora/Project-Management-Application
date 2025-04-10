package com.project_management_backend.Project.Management.controller.team;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project_management_backend.Project.Management.model.dto.CollectionResponseDTO;
import com.project_management_backend.Project.Management.model.dto.team.TeamRequestDTO;
import com.project_management_backend.Project.Management.model.dto.team.TeamResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/teams")
@Tag(name = "Team Management", description = "Operations for managing teams")
public interface TeamController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all teams")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    public CollectionResponseDTO<TeamResponseDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get team by ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER', 'TEAM_MEMBER')")
    public TeamResponseDTO findById(@PathVariable Integer id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new team")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    public TeamResponseDTO save(@Valid @RequestBody TeamRequestDTO teamRequestDTO);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing team")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    public TeamResponseDTO update(@PathVariable Integer id, @Valid @RequestBody TeamRequestDTO teamRequestDTO);


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a team")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id);

}
