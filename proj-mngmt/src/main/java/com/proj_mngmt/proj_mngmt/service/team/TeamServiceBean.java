package com.proj_mngmt.proj_mngmt.service.team;

import com.proj_mngmt.proj_mngmt.model.dto.CollectionResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.TeamEntity;
import com.proj_mngmt.proj_mngmt.model.mapper.TeamMapper;
import com.proj_mngmt.proj_mngmt.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class TeamServiceBean implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @Override
    public CollectionResponseDTO<TeamResponseDTO> findAll(int page, int size) {
        Page<TeamEntity> teamsPage = teamRepository.findAll(PageRequest.of(page, size));

        List<TeamResponseDTO> teamDTOs = teamsPage.getContent().stream()
                .map(teamMapper::convertEntityToResponseDto)
                .collect(Collectors.toList());

        return CollectionResponseDTO.<TeamResponseDTO>builder()
                .pageNumber(page)
                .pageSize(size)
                .totalPages(teamsPage.getTotalPages())
                .totalElements(teamsPage.getTotalElements())
                .elements(teamDTOs)
                .build();
    }
    @Override
    public TeamResponseDTO findById(Integer id) {
        TeamEntity team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with ID: " + id));

        return teamMapper.convertEntityToResponseDto(team);
    }

    @Transactional
    @Override
    public TeamResponseDTO save(TeamRequestDTO teamRequestDTO) {
        if (teamRepository.existsByTeamName(teamRequestDTO.teamName())) {
            throw new IllegalArgumentException("Team with name " + teamRequestDTO.teamName() + " already exists");
        }

        TeamEntity team = teamMapper.convertRequestDtoToEntity(teamRequestDTO);
        TeamEntity savedTeam = teamRepository.save(team);

        return teamMapper.convertEntityToResponseDto(savedTeam);
    }

    @Transactional
    @Override
    public TeamResponseDTO update(Integer id, TeamRequestDTO teamRequestDTO) {
        TeamEntity team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with ID: " + id));

        teamMapper.updateTeamEntity(team, teamRequestDTO);
        TeamEntity updatedTeam = teamRepository.save(team);

        return teamMapper.convertEntityToResponseDto(updatedTeam);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        if (!teamRepository.existsById(id)) {
            throw new EntityNotFoundException("Team not found with ID: " + id);
        }

        teamRepository.deleteById(id);
    }
}
