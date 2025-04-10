package com.project_management_backend.Project.Management.repository;

import java.util.List;

import com.project_management_backend.Project.Management.model.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    List<TaskEntity> findByProjectId(Integer projectId);

    List<TaskEntity> findByAssignedUserId(Integer userId);

    List<TaskEntity> findByStatus(String status);

    List<TaskEntity> findByProjectIdAndStatus(Integer projectId, String status);

    List<TaskEntity> findByAssignedUserIdAndStatus(Integer userId, String status);
}