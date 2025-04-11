package com.proj_mngmt.proj_mngmt.model.mapper;

import com.proj_mngmt.proj_mngmt.model.dto.team.TeamRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.TeamEntity;
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