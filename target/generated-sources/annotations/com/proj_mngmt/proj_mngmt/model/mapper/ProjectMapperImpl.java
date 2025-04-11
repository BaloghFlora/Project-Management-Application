package com.proj_mngmt.proj_mngmt.model.mapper;

import com.proj_mngmt.proj_mngmt.model.dto.project.ProjectRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.project.ProjectResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.ProjectEntity;
import com.proj_mngmt.proj_mngmt.model.entity.TeamEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-11T21:57:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public ProjectEntity convertRequestDtoToEntity(ProjectRequestDTO requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        ProjectEntity projectEntity = new ProjectEntity();

        projectEntity.setProjectName( requestDto.projectName() );
        projectEntity.setDescription( requestDto.description() );

        return projectEntity;
    }

    @Override
    public ProjectResponseDTO convertEntityToResponseDto(ProjectEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Integer teamId = null;
        String teamName = null;
        Integer id = null;
        String projectName = null;
        String description = null;

        teamId = entityTeamId( entity );
        teamName = entityTeamTeamName( entity );
        id = entity.getId();
        projectName = entity.getProjectName();
        description = entity.getDescription();

        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO( id, projectName, description, teamId, teamName );

        return projectResponseDTO;
    }

    @Override
    public void updateProjectEntity(ProjectEntity projectEntity, ProjectRequestDTO projectRequestDTO) {
        if ( projectRequestDTO == null ) {
            return;
        }

        projectEntity.setProjectName( projectRequestDTO.projectName() );
        projectEntity.setDescription( projectRequestDTO.description() );
    }

    private Integer entityTeamId(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        TeamEntity team = projectEntity.getTeam();
        if ( team == null ) {
            return null;
        }
        Integer id = team.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityTeamTeamName(ProjectEntity projectEntity) {
        if ( projectEntity == null ) {
            return null;
        }
        TeamEntity team = projectEntity.getTeam();
        if ( team == null ) {
            return null;
        }
        String teamName = team.getTeamName();
        if ( teamName == null ) {
            return null;
        }
        return teamName;
    }
}
