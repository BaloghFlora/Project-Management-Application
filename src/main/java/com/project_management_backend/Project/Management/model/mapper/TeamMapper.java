package com.project_management_backend.Project.Management.model.mapper;

import com.project_management_backend.Project.Management.model.dto.team.TeamRequestDTO;
import com.project_management_backend.Project.Management.model.dto.team.TeamResponseDTO;
import com.project_management_backend.Project.Management.model.entity.TeamEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TeamMapper extends DtoMapper<TeamEntity, TeamRequestDTO, TeamResponseDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    TeamEntity convertRequestDtoToEntity(TeamRequestDTO requestDto);

    @Override
    TeamResponseDTO convertEntityToResponseDto(TeamEntity entity);

    @Mapping(target = "id", ignore = true)
    void updateTeamEntity(@MappingTarget TeamEntity teamEntity, TeamRequestDTO teamRequestDTO);
}