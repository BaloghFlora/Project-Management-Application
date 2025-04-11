package com.proj_mngmt.proj_mngmt.controller.team;

import com.proj_mngmt.proj_mngmt.model.dto.CollectionResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamResponseDTO;
import com.proj_mngmt.proj_mngmt.service.team.TeamService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public void delete(@PathVariable Integer id) {
        teamService.delete(id);
    }
}
