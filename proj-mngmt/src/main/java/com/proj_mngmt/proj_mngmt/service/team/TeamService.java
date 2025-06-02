package com.proj_mngmt.proj_mngmt.service.team;

import com.proj_mngmt.proj_mngmt.model.dto.CollectionResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamMemberResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.TeamEntity;
import com.proj_mngmt.proj_mngmt.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

public interface TeamService {
    public CollectionResponseDTO<TeamResponseDTO> findAll(int page, int size) ;
    public TeamResponseDTO findById(Integer id);

    public TeamResponseDTO save(TeamRequestDTO teamRequestDTO);
    public TeamResponseDTO update(Integer id, TeamRequestDTO teamRequestDTO);
    public void delete(Integer id);

    public CollectionResponseDTO<TeamMemberResponseDTO> getTeamMembers(Integer teamId, Pageable pageable);

    @Transactional
    public TeamMemberResponseDTO addTeamMember(Integer teamId, Integer userId);
    @Transactional
    public void removeTeamMember(Integer teamId, Integer userId);

    public CollectionResponseDTO<TeamMemberResponseDTO> getAvailableUsers(Pageable pageable);

    public TeamMemberResponseDTO convertToTeamMemberResponse(UserEntity user);

    }
