package com.proj_mngmt.proj_mngmt.model.mapper;

import com.proj_mngmt.proj_mngmt.model.dto.project.ProjectRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.project.ProjectResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProjectMapper extends DtoMapper<ProjectEntity, ProjectRequestDTO, ProjectResponseDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    ProjectEntity convertRequestDtoToEntity(ProjectRequestDTO requestDto);

    @Override
    @Mapping(target = "teamId", source = "team.id")
    @Mapping(target = "teamName", source = "team.teamName")
    ProjectResponseDTO convertEntityToResponseDto(ProjectEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    void updateProjectEntity(@MappingTarget ProjectEntity projectEntity, ProjectRequestDTO projectRequestDTO);
}