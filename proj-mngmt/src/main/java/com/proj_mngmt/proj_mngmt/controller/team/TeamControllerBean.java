package com.proj_mngmt.proj_mngmt.controller.team;

import com.proj_mngmt.proj_mngmt.model.dto.CollectionResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.AddTeamMemberRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamMemberResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamResponseDTO;
import com.proj_mngmt.proj_mngmt.service.team.TeamService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

@AllArgsConstructor
@RestController
public class TeamControllerBean implements TeamController{


    private final TeamService teamService;

    @Override
    public CollectionResponseDTO<TeamResponseDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return teamService.findAll(page, size);
    }

    @Override
    public TeamResponseDTO findById(@PathVariable Integer id) {
        return teamService.findById(id);
    }
    @Override
    public TeamResponseDTO save(@Valid @RequestBody TeamRequestDTO teamRequestDTO) {
        return teamService.save(teamRequestDTO);
    }

    @Override
    public TeamResponseDTO update(@PathVariable Integer id, @Valid @RequestBody TeamRequestDTO teamRequestDTO) {
        return teamService.update(id, teamRequestDTO);
    }

    @Override
    public ResponseEntity<CollectionResponseDTO<TeamMemberResponseDTO>> getTeamMembers(
            @PathVariable Integer teamId, Pageable pageable) {
        CollectionResponseDTO<TeamMemberResponseDTO> members = teamService.getTeamMembers(teamId, pageable);
        return ResponseEntity.ok(members);
    }

    @Override
    public ResponseEntity<TeamMemberResponseDTO> addTeamMember(
            @PathVariable Integer teamId,
            @Valid @RequestBody AddTeamMemberRequestDTO request) {
        TeamMemberResponseDTO member = teamService.addTeamMember(teamId, request.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @Override
    public ResponseEntity<Void> removeTeamMember(@PathVariable Integer teamId,
                                                 @PathVariable Integer userId) {
        teamService.removeTeamMember(teamId, userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CollectionResponseDTO<TeamMemberResponseDTO>> getAvailableUsers(Pageable pageable) {
        CollectionResponseDTO<TeamMemberResponseDTO> users = teamService.getAvailableUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @Override
    public void delete(@PathVariable Integer id) {
        teamService.delete(id);
    }

}
