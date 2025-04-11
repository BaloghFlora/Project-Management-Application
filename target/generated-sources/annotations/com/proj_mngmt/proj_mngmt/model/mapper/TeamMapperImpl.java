package com.proj_mngmt.proj_mngmt.model.mapper;

import com.proj_mngmt.proj_mngmt.model.dto.team.TeamRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.team.TeamResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.TeamEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-11T21:57:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class TeamMapperImpl implements TeamMapper {

    @Override
    public TeamEntity convertRequestDtoToEntity(TeamRequestDTO requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        TeamEntity teamEntity = new TeamEntity();

        teamEntity.setTeamName( requestDto.teamName() );
        teamEntity.setDescription( requestDto.description() );

        return teamEntity;
    }

    @Override
    public TeamResponseDTO convertEntityToResponseDto(TeamEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Integer id = null;
        String teamName = null;
        String description = null;

        id = entity.getId();
        teamName = entity.getTeamName();
        description = entity.getDescription();

        TeamResponseDTO teamResponseDTO = new TeamResponseDTO( id, teamName, description );

        return teamResponseDTO;
    }

    @Override
    public void updateTeamEntity(TeamEntity teamEntity, TeamRequestDTO teamRequestDTO) {
        if ( teamRequestDTO == null ) {
            return;
        }

        teamEntity.setTeamName( teamRequestDTO.teamName() );
        teamEntity.setDescription( teamRequestDTO.description() );
    }
}
