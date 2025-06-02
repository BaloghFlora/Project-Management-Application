package com.proj_mngmt.proj_mngmt.controller.team;

import com.proj_mngmt.proj_mngmt.model.dto.CollectionResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.AddTeamMemberRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamMemberResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/teams")
@Tag(name = "Team Management", description = "Operations for managing teams")
public interface TeamController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all teams")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER')")
    public CollectionResponseDTO<TeamResponseDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get team by ID")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER') or hasRole('TEAM_MEMBER')")
    public TeamResponseDTO findById(@PathVariable Integer id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new team")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER')")
    public TeamResponseDTO save(@Valid @RequestBody TeamRequestDTO teamRequestDTO);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing team")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER')")
    public TeamResponseDTO update(@PathVariable Integer id, @Valid @RequestBody TeamRequestDTO teamRequestDTO);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a team")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id);

    @GetMapping("/{teamId}/members")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER')")
    public ResponseEntity<CollectionResponseDTO<TeamMemberResponseDTO>> getTeamMembers(
            @PathVariable Integer teamId, Pageable pageable);

    @PostMapping("/{teamId}/members")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeamMemberResponseDTO> addTeamMember(
            @PathVariable Integer teamId,
            @Valid @RequestBody AddTeamMemberRequestDTO request);

    @DeleteMapping("/{teamId}/members/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeTeamMember(@PathVariable Integer teamId,
                                                 @PathVariable Integer userId);

    @GetMapping("/available-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CollectionResponseDTO<TeamMemberResponseDTO>> getAvailableUsers(Pageable pageable);
}