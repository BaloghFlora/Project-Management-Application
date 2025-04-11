package com.proj_mngmt.proj_mngmt.model.mapper;

import com.proj_mngmt.proj_mngmt.model.dto.task.TaskRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.task.TaskResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.TaskEntity;
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