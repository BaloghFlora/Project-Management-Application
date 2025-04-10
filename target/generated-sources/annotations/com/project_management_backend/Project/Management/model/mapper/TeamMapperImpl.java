package com.project_management_backend.Project.Management.model.mapper;

import com.project_management_backend.Project.Management.model.dto.team.TeamRequestDTO;
import com.project_management_backend.Project.Management.model.dto.team.TeamResponseDTO;
import com.project_management_backend.Project.Management.model.entity.TeamEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-10T22:49:33+0300",
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
