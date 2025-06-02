package com.proj_mngmt.proj_mngmt.service.team;

import com.proj_mngmt.proj_mngmt.model.dto.CollectionResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamMemberResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.TeamEntity;
import com.proj_mngmt.proj_mngmt.model.entity.UserEntity;
import com.proj_mngmt.proj_mngmt.model.mapper.TeamMapper;
import com.proj_mngmt.proj_mngmt.model.mapper.UserMapper;
import com.proj_mngmt.proj_mngmt.repository.TeamRepository;
import com.proj_mngmt.proj_mngmt.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class TeamServiceBean implements TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMapper teamMapper;
    private final UserMapper userMapper;

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


    public CollectionResponseDTO<TeamMemberResponseDTO> getTeamMembers(Integer teamId, Pageable pageable) {
        if (!teamRepository.existsById(teamId)) {
            throw new RuntimeException("Team not found with id: " + teamId);
        }

        Page<UserEntity> members = userRepository.findUsersByTeamId(teamId, pageable);
        return CollectionResponseDTO.<TeamMemberResponseDTO>builder()
                .pageNumber(members.getNumber())
                .pageSize(members.getSize())
                .totalPages(members.getTotalPages())
                .totalElements(members.getTotalElements())
                .elements(members.getContent().stream()
                        .map(this::convertToTeamMemberResponse)
                        .toList())
                .build();
    }

    @Transactional
    public TeamMemberResponseDTO addTeamMember(Integer teamId, Integer userId) {
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + teamId));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (user.getTeam() != null) {
            throw new RuntimeException("User is already assigned to a team");
        }

        user.setTeam(team);
        UserEntity updatedUser = userRepository.save(user);
        return convertToTeamMemberResponse(updatedUser);
    }

    @Transactional
    public void removeTeamMember(Integer teamId, Integer userId) {
        if (!teamRepository.existsById(teamId)) {
            throw new RuntimeException("Team not found with id: " + teamId);
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (user.getTeam() == null || !user.getTeam().getId().equals(teamId)) {
            throw new RuntimeException("User is not a member of this team");
        }

        user.setTeam(null);
        userRepository.save(user);
    }


    public CollectionResponseDTO<TeamMemberResponseDTO> getAvailableUsers(Pageable pageable) {
        Page<UserEntity> availableUsers = userRepository.findByTeamIsNull(pageable);
        return CollectionResponseDTO.<TeamMemberResponseDTO>builder()
                .pageNumber(availableUsers.getNumber())
                .pageSize(availableUsers.getSize())
                .totalPages(availableUsers.getTotalPages())
                .totalElements(availableUsers.getTotalElements())
                .elements(availableUsers.getContent().stream()
                        .map(this::convertToTeamMemberResponse)
                        .toList())
                .build();
    }

    public TeamMemberResponseDTO convertToTeamMemberResponse(UserEntity user) {
        return new TeamMemberResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getTeam() != null ? user.getTeam().getId() : null,
                user.getTeam() != null ? user.getTeam().getTeamName() : null
        );
    }
}
