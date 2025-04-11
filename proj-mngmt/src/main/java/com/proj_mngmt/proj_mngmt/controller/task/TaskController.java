package com.proj_mngmt.proj_mngmt.controller.task;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import com.proj_mngmt.proj_mngmt.model.dto.CollectionResponseDTO;
import com.proj_mngmt.proj_mngmt.model.dto.task.TaskRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.task.TaskResponseDTO;
import com.proj_mngmt.proj_mngmt.service.task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RequestMapping("/v1/tasks")
@Tag(name = "Task Management", description = "Operations for managing tasks")

public interface TaskController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all tasks")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    public CollectionResponseDTO<TaskResponseDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @GetMapping("/project/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get tasks by project ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER', 'TEAM_MEMBER')")
    public CollectionResponseDTO<TaskResponseDTO> findByProjectId(
            @PathVariable Integer projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);


    //not working yet
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get tasks assigned to a user")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER') or @authService.isSelf(#userId)")
    public CollectionResponseDTO<TaskResponseDTO> findByAssignedUserId(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws AccessDeniedException;
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get task by ID")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER', 'TEAM_MEMBER')")
    public TaskResponseDTO findById(@PathVariable Integer id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new task")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER', 'TEAM_MEMBER')")
    public TaskResponseDTO save(@Valid @RequestBody TaskRequestDTO taskRequestDTO);
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing task")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER') or #taskService.isAssignedToUser(#id, authentication.principal.id)")
    public TaskResponseDTO update(@PathVariable Integer id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) throws AccessDeniedException;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a task")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    public void delete(@PathVariable Integer id) throws AccessDeniedException;

}
