package com.project_management_backend.Project.Management.controller.team;

import com.project_management_backend.Project.Management.model.dto.CollectionResponseDTO;
import com.project_management_backend.Project.Management.model.dto.team.TeamRequestDTO;
import com.project_management_backend.Project.Management.model.dto.team.TeamResponseDTO;
import com.project_management_backend.Project.Management.service.team.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
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
    public void delete(@PathVariable Integer id) {
        teamService.delete(id);
    }
}
