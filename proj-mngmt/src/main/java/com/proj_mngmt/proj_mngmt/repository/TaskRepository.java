package com.proj_mngmt.proj_mngmt.repository;

import com.proj_mngmt.proj_mngmt.model.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    List<TaskEntity> findByProjectId(Integer projectId);

    List<TaskEntity> findByAssignedUserId(Integer userId);

    List<TaskEntity> findByStatus(String status);

    List<TaskEntity> findByProjectIdAndStatus(Integer projectId, String status);

    List<TaskEntity> findByAssignedUserIdAndStatus(Integer userId, String status);
}