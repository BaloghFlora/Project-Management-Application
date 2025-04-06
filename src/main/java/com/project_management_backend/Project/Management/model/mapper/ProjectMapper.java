package com.project_management_backend.Project.Management.model.mapper;

import com.project_management_backend.Project.Management.model.dto.project.ProjectRequestDTO;
import com.project_management_backend.Project.Management.model.dto.project.ProjectResponseDTO;
import com.project_management_backend.Project.Management.model.entity.ProjectEntity;
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