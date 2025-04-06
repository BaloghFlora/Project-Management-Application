package com.project_management_backend.Project.Management.model.mapper;

import com.project_management_backend.Project.Management.model.dto.task.TaskRequestDTO;
import com.project_management_backend.Project.Management.model.dto.task.TaskResponseDTO;
import com.project_management_backend.Project.Management.model.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper extends DtoMapper<TaskEntity, TaskRequestDTO, TaskResponseDTO> {

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "assignedUser", ignore = true)
    TaskEntity convertRequestDtoToEntity(TaskRequestDTO requestDto);

    @Override
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "projectName", source = "project.projectName")
    @Mapping(target = "assignedUserId", source = "assignedUser.id")
    @Mapping(target = "assignedUserName", source = "assignedUser.name")
    TaskResponseDTO convertEntityToResponseDto(TaskEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "assignedUser", ignore = true)
    void updateTaskEntity(@MappingTarget TaskEntity taskEntity, TaskRequestDTO taskRequestDTO);
}