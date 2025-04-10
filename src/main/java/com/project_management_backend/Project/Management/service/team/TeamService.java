package com.project_management_backend.Project.Management.service.team;

import com.project_management_backend.Project.Management.model.dto.CollectionResponseDTO;
import com.project_management_backend.Project.Management.model.dto.team.TeamRequestDTO;
import com.project_management_backend.Project.Management.model.dto.team.TeamResponseDTO;

public interface TeamService {
    public CollectionResponseDTO<TeamResponseDTO> findAll(int page, int size) ;
    public TeamResponseDTO findById(Integer id);

    public TeamResponseDTO save(TeamRequestDTO teamRequestDTO);
    public TeamResponseDTO update(Integer id, TeamRequestDTO teamRequestDTO);
    public void delete(Integer id);

    }
