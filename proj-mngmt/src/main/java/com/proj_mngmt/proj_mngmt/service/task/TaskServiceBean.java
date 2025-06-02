package com.proj_mngmt.proj_mngmt.service.task;

import com.proj_mngmt.proj_mngmt.model.dto.CollectionResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.task.TaskRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.task.TaskResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.ProjectEntity;
import com.proj_mngmt.proj_mngmt.model.entity.Role;
import com.proj_mngmt.proj_mngmt.model.entity.TaskEntity;
import com.proj_mngmt.proj_mngmt.model.entity.UserEntity;
import com.proj_mngmt.proj_mngmt.model.mapper.TaskMapper;
import com.proj_mngmt.proj_mngmt.repository.ProjectRepository;
import com.proj_mngmt.proj_mngmt.repository.TaskRepository;
import com.proj_mngmt.proj_mngmt.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskServiceBean implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Override
    public CollectionResponseDTO<TaskResponseDTO> findAll(int page, int size) {
        log.debug("Finding all tasks - page: {}, size: {}", page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<TaskEntity> tasksPage = taskRepository.findAll(pageable);

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
        log.debug("Finding tasks by project ID: {} - page: {}, size: {}", projectId, page, size);

        // Verify project exists
        if (!projectRepository.existsById(projectId)) {
            throw new EntityNotFoundException("Project not found with ID: " + projectId);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<TaskEntity> tasksPage = taskRepository.findByProjectIdWithDetails(projectId, pageable);

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
    public CollectionResponseDTO<TaskResponseDTO> findByAssignedUserId(Integer userId, int page, int size) throws AccessDeniedException {
        log.debug("Finding tasks by assigned user ID: {} - page: {}, size: {}", userId, page, size);

        // Verify user exists
        UserEntity targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        // Check permissions
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = getCurrentUser(authentication);

        if (!canViewUserTasks(currentUser, targetUser)) {
            throw new AccessDeniedException("You do not have permission to view tasks for this user");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<TaskEntity> tasksPage = taskRepository.findByAssignedUserIdWithDetails(userId, pageable);

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
    public TaskResponseDTO findById(Integer id) {
        log.debug("Finding task by ID: {}", id);

        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));

        return taskMapper.convertEntityToResponseDto(task);
    }

    @Transactional
    @Override
    public TaskResponseDTO save(TaskRequestDTO taskRequestDTO) {
        log.debug("Creating new task: {}", taskRequestDTO.taskName());

        validateTaskRequest(taskRequestDTO);

        TaskEntity task = taskMapper.convertRequestDtoToEntity(taskRequestDTO);

        // Set project if provided
        if (taskRequestDTO.projectId() != null) {
            ProjectEntity project = projectRepository.findById(taskRequestDTO.projectId())
                    .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + taskRequestDTO.projectId()));
            task.setProject(project);

            // Verify current user has access to this project
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserEntity currentUser = getCurrentUser(authentication);
            if (!canAccessProject(currentUser, project)) {
                throw new AccessDeniedException("You do not have access to this project");
            }
        }

        // Set assigned user if provided
        if (taskRequestDTO.assignedUserId() != null) {
            UserEntity assignedUser = userRepository.findById(taskRequestDTO.assignedUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + taskRequestDTO.assignedUserId()));
            task.setAssignedUser(assignedUser);
        }

        TaskEntity savedTask = taskRepository.save(task);
        log.info("Task created successfully with ID: {}", savedTask.getId());

        return taskMapper.convertEntityToResponseDto(savedTask);
    }

    @Transactional
    @Override
    public TaskResponseDTO update(Integer id, TaskRequestDTO taskRequestDTO) throws AccessDeniedException {
        log.debug("Updating task ID: {} with data: {}", id, taskRequestDTO.taskName());

        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));

        // Check permissions
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = getCurrentUser(authentication);

        if (!canModifyTask(currentUser, task)) {
            throw new AccessDeniedException("You do not have permission to update this task");
        }

        validateTaskRequest(taskRequestDTO);

        // Update basic fields
        taskMapper.updateTaskEntity(task, taskRequestDTO);



        TaskEntity updatedTask = taskRepository.save(task);
        log.info("Task updated successfully: {}", updatedTask.getId());

        return taskMapper.convertEntityToResponseDto(updatedTask);
    }

    @Transactional
    @Override
    public void delete(Integer id) throws AccessDeniedException {
        log.debug("Deleting task ID: {}", id);

        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with ID: " + id));

        // Check permissions
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = getCurrentUser(authentication);

        if (!canDeleteTask(currentUser, task)) {
            throw new AccessDeniedException("You do not have permission to delete this task");
        }

        taskRepository.delete(task);
        log.info("Task deleted successfully: {}", id);
    }

    // Helper methods for permission checking
    private UserEntity getCurrentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Current user not found"));
    }

    private boolean canViewUserTasks(UserEntity currentUser, UserEntity targetUser) {
        // Admin and Project Managers can view anyone's tasks
        if (currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.PROJECT_MANAGER) {
            return true;
        }

        // Users can view their own tasks
        return currentUser.getId().equals(targetUser.getId());
    }

    private boolean canAccessProject(UserEntity user, ProjectEntity project) {
        // Admin can access any project
        if (user.getRole() == Role.ADMIN) {
            return true;
        }

        // Project managers and team members can access projects in their team
        return user.getTeam() != null && user.getTeam().getId().equals(project.getTeam().getId());
    }

    private boolean canModifyTask(UserEntity user, TaskEntity task) {
        // Admin and Project Managers can modify any task
        if (user.getRole() == Role.ADMIN || user.getRole() == Role.PROJECT_MANAGER) {
            return true;
        }

        // Team members can modify tasks assigned to them
        return task.getAssignedUser() != null && task.getAssignedUser().getId().equals(user.getId());
    }

    private boolean canDeleteTask(UserEntity user, TaskEntity task) {
        // Only Admin and Project Managers can delete tasks
        return user.getRole() == Role.ADMIN || user.getRole() == Role.PROJECT_MANAGER;
    }

    private void validateTaskRequest(TaskRequestDTO taskRequestDTO) {
        if (taskRequestDTO.taskName() == null || taskRequestDTO.taskName().trim().isEmpty()) {
            throw new IllegalArgumentException("Task name is required");
        }

        // Add more validation as needed
        if (taskRequestDTO.status() == null || taskRequestDTO.status().trim().isEmpty()) {
            throw new IllegalArgumentException("Task status is required");
        }
    }
}