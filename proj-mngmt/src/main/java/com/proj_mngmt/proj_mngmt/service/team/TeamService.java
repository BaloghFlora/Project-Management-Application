package com.proj_mngmt.proj_mngmt.service.team;

import com.proj_mngmt.proj_mngmt.model.dto.CollectionResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamResponseDTO;

public interface TeamService {
    public CollectionResponseDTO<TeamResponseDTO> findAll(int page, int size) ;
    public TeamResponseDTO findById(Integer id);

    public TeamResponseDTO save(TeamRequestDTO teamRequestDTO);
    public TeamResponseDTO update(Integer id, TeamRequestDTO teamRequestDTO);
    public void delete(Integer id);

    }
