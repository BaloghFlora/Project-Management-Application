package com.proj_mngmt.proj_mngmt.service.task;

import com.proj_mngmt.proj_mngmt.model.dto.CollectionResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.task.TaskRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.task.TaskResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.ProjectEntity;
import com.proj_mngmt.proj_mngmt.model.entity.TaskEntity;
import com.proj_mngmt.proj_mngmt.model.entity.UserEntity;
import com.proj_mngmt.proj_mngmt.model.mapper.TaskMapper;
import com.proj_mngmt.proj_mngmt.repository.ProjectRepository;
import com.proj_mngmt.proj_mngmt.repository.TaskRepository;
import com.proj_mngmt.proj_mngmt.repository.UserRepository;
import com.proj_mngmt.proj_mngmt.security.service.user.UserDetailsServiceBean;
import com.proj_mngmt.proj_mngmt.security.service.user.UserDetailsServiceBean;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TaskServiceBean implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;


    @Override
    public CollectionResponseDTO<TaskResponseDTO> findAll(int page, int size) {
        Page<TaskEntity> tasksPage = taskRepository.findAll(PageRequest.of(page, size));

        List<TaskResponseDTO> taskDTOs = tasksPage.getContent().stream()
                .map(taskMapper::convertEntityToResponseDto)
                .collect(Collectors.toList());

        return CollectionResponseDTO.<TaskResponseDTO>builder()
                .pageNumber(page)
                .pageSize(size)
                .totalPages(tasksPage.getTotalPages())
                .totalElements(tasksPage.getTotalElements())
                .elements(taskDTOs)
                .build();
    }

    @Override
    public CollectionResponseDTO<TaskResponseDTO> findByProjectId(Integer projectId, int page, int size) {
        // Check if project exists
        if (!projectRepository.existsById(projectId)) {
            throw new EntityNotFoundException("Project not found with ID: " + projectId);
        }

        List<TaskEntity> tasks = taskRepository.findByProjectId(projectId);

        List<TaskResponseDTO> taskDTOs = tasks.stream()
                .map(taskMapper::convertEntityToResponseDto)
                .collect(Collectors.toList());

        // Manually handle pagination
        int start = page * size;
        int end = Math.min(start + size, taskDTOs.size());
        List<TaskResponseDTO> paginatedTasks = start < taskDTOs.size() ? taskDTOs.subList(start, end) : List.of();

        return CollectionResponseDTO.<TaskResponseDTO>builder()
                .pageNumber(page)
                .pageSize(size)
                .totalPages((taskDTOs.size() + size - 1) / size)
                .totalElements(taskDTOs.size())
                .elements(paginatedTasks)
                .build();
    }

    @Override
    public CollectionResponseDTO<TaskResponseDTO> findByAssignedUserId(Integer userId, int page, int size) throws AccessDeniedException {
        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }

        // Check if current user is the user or has admin/project manager role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getAuthorities().stream().anyMatch(a ->
                        a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_PROJECT_MANAGER"))) {
            throw new AccessDeniedException("Access denied");
        }

        List<TaskEntity> tasks = taskRepository.findByAssignedUserId(userId);

        List<TaskResponseDTO> taskDTOs = tasks.stream()
                .map(taskMapper::convertEntityToResponseDto)
                .collect(Collectors.toList());

        // Manual pagination
        int start = page * size;
        int end = Math.min(start + size, taskDTOs.size());
        List<TaskResponseDTO> paginatedTasks = start < taskDTOs.size() ? taskDTOs.subList(start, end) : List.of();

        return CollectionResponseDTO.<TaskResponseDTO>builder()
                .pageNumber(page)
                .pageSize(size)
                .totalPages((taskDTOs.size() + size - 1) / size)
                .totalElements(taskDTOs.size())
                .elements(paginatedTasks)
                .build();
    }

    public TaskResponseDTO findById(Integer id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));

        return taskMapper.convertEntityToResponseDto(task);
    }

    @Transactional
    public TaskResponseDTO save(TaskRequestDTO taskRequestDTO) {
        TaskEntity task = taskMapper.convertRequestDtoToEntity(taskRequestDTO);

        // Set project if provided
        if (taskRequestDTO.projectId() != null) {
            ProjectEntity project = projectRepository.findById(taskRequestDTO.projectId())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + taskRequestDTO.projectId()));
            task.setProject(project);
        }

        // Set assigned user if provided
        if (taskRequestDTO.assignedUserId() != null) {
            UserEntity user = userRepository.findById(taskRequestDTO.assignedUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + taskRequestDTO.assignedUserId()));
            task.setAssignedUser(user);
        }

        TaskEntity savedTask = taskRepository.save(task);
        return taskMapper.convertEntityToResponseDto(savedTask);
    }

    @Transactional
    public TaskResponseDTO update(Integer id, TaskRequestDTO taskRequestDTO) throws AccessDeniedException {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));

        // Check permissions - only admin, project manager, or the assigned user can update
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isProjectManager = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PROJECT_MANAGER"));

        if (!isAdmin && !isProjectManager) {
            throw new AccessDeniedException("You do not have permission to update this task");
        }

        taskMapper.updateTaskEntity(task, taskRequestDTO);

        // Update project if changed
        if (taskRequestDTO.projectId() != null) {
            ProjectEntity project = projectRepository.findById(taskRequestDTO.projectId())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + taskRequestDTO.projectId()));
            task.setProject(project);
        }

        // Update assigned user if changed
        if (taskRequestDTO.assignedUserId() != null) {
            UserEntity user = userRepository.findById(taskRequestDTO.assignedUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + taskRequestDTO.assignedUserId()));
            task.setAssignedUser(user);
        }

        TaskEntity updatedTask = taskRepository.save(task);
        return taskMapper.convertEntityToResponseDto(updatedTask);
    }

    @Transactional
    public void delete(Integer id) throws AccessDeniedException {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));

        // Check permissions - only admin or project manager can delete
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isProjectManager = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_PROJECT_MANAGER"));

        if (!isAdmin && !isProjectManager) {
            throw new AccessDeniedException("You do not have permission to delete this task");
        }

        taskRepository.delete(task);
    }
}
