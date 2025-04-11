package com.proj_mngmt.proj_mngmt.controller.task;

import com.proj_mngmt.proj_mngmt.model.dto.CollectionResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.task.TaskRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.task.TaskResponseDTO;
import com.proj_mngmt.proj_mngmt.service.task.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@AllArgsConstructor
public class TaskControllerBean implements TaskController{

    private final TaskService taskService;
    @Override
    public CollectionResponseDTO<TaskResponseDTO> findAll(int page, int size) {
        return taskService.findAll(page, size);
    }

    @Override
    public CollectionResponseDTO<TaskResponseDTO> findByProjectId(Integer projectId, int page, int size) {
        return taskService.findByProjectId(projectId, page, size);
    }

    @Override
    public CollectionResponseDTO<TaskResponseDTO> findByAssignedUserId(Integer userId, int page, int size) throws AccessDeniedException {
        return taskService.findByAssignedUserId(userId, page, size);
    }

    @Override
    public TaskResponseDTO findById(Integer id) {
        return taskService.findById(id);
    }

    @Override
    public TaskResponseDTO save(TaskRequestDTO taskRequestDTO) {
        return taskService.save(taskRequestDTO);
    }

    @Override
    public TaskResponseDTO update(Integer id, TaskRequestDTO taskRequestDTO) throws AccessDeniedException {
        return taskService.update(id, taskRequestDTO);
    }

    @Override
    public void delete(Integer id) throws AccessDeniedException {
        taskService.delete(id);
    }
}
