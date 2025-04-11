package com.proj_mngmt.proj_mngmt.model.mapper;

import com.proj_mngmt.proj_mngmt.model.dto.task.TaskRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.task.TaskResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.ProjectEntity;
import com.proj_mngmt.proj_mngmt.model.entity.TaskEntity;
import com.proj_mngmt.proj_mngmt.model.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-11T21:57:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskEntity convertRequestDtoToEntity(TaskRequestDTO requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setTaskName( requestDto.taskName() );
        taskEntity.setDescription( requestDto.description() );
        taskEntity.setStatus( requestDto.status() );

        return taskEntity;
    }

    @Override
    public TaskResponseDTO convertEntityToResponseDto(TaskEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Integer projectId = null;
        String projectName = null;
        Integer assignedUserId = null;
        String assignedUserName = null;
        Integer id = null;
        String taskName = null;
        String description = null;
        String status = null;

        projectId = entityProjectId( entity );
        projectName = entityProjectProjectName( entity );
        assignedUserId = entityAssignedUserId( entity );
        assignedUserName = entityAssignedUserName( entity );
        id = entity.getId();
        taskName = entity.getTaskName();
        description = entity.getDescription();
        status = entity.getStatus();

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO( id, taskName, description, status, projectId, projectName, assignedUserId, assignedUserName );

        return taskResponseDTO;
    }

    @Override
    public void updateTaskEntity(TaskEntity taskEntity, TaskRequestDTO taskRequestDTO) {
        if ( taskRequestDTO == null ) {
            return;
        }

        taskEntity.setTaskName( taskRequestDTO.taskName() );
        taskEntity.setDescription( taskRequestDTO.description() );
        taskEntity.setStatus( taskRequestDTO.status() );
    }

    private Integer entityProjectId(TaskEntity taskEntity) {
        if ( taskEntity == null ) {
            return null;
        }
        ProjectEntity project = taskEntity.getProject();
        if ( project == null ) {
            return null;
        }
        Integer id = project.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityProjectProjectName(TaskEntity taskEntity) {
        if ( taskEntity == null ) {
            return null;
        }
        ProjectEntity project = taskEntity.getProject();
        if ( project == null ) {
            return null;
        }
        String projectName = project.getProjectName();
        if ( projectName == null ) {
            return null;
        }
        return projectName;
    }

    private Integer entityAssignedUserId(TaskEntity taskEntity) {
        if ( taskEntity == null ) {
            return null;
        }
        UserEntity assignedUser = taskEntity.getAssignedUser();
        if ( assignedUser == null ) {
            return null;
        }
        Integer id = assignedUser.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityAssignedUserName(TaskEntity taskEntity) {
        if ( taskEntity == null ) {
            return null;
        }
        UserEntity assignedUser = taskEntity.getAssignedUser();
        if ( assignedUser == null ) {
            return null;
        }
        String name = assignedUser.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
