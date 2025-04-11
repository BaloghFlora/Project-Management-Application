package com.proj_mngmt.proj_mngmt.service.task;

import com.proj_mngmt.proj_mngmt.model.dto.CollectionResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.task.TaskRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.task.TaskResponseDTO;
import com.proj_mngmt.proj_mngmt.model.mapper.TaskMapper;
import com.proj_mngmt.proj_mngmt.repository.ProjectRepository;
import com.proj_mngmt.proj_mngmt.repository.TaskRepository;
import com.proj_mngmt.proj_mngmt.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;

public interface TaskService {
    public CollectionResponseDTO<TaskResponseDTO> findAll(int page, int size);

    public CollectionResponseDTO<TaskResponseDTO> findByProjectId(Integer projectId, int page, int size);

    public CollectionResponseDTO<TaskResponseDTO> findByAssignedUserId(Integer userId, int page, int size) throws AccessDeniedException;

    public TaskResponseDTO findById(Integer id);

    @Transactional
    public TaskResponseDTO save(TaskRequestDTO taskRequestDTO);
    @Transactional
    public TaskResponseDTO update(Integer id, TaskRequestDTO taskRequestDTO) throws AccessDeniedException;
    @Transactional
    public void delete(Integer id) throws AccessDeniedException;
}
