package com.proj_mngmt.proj_mngmt.repository;

import com.proj_mngmt.proj_mngmt.model.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    // Paginated methods
    Page<TaskEntity> findByProject_Id(Integer projectId, Pageable pageable);

    Page<TaskEntity> findByAssignedUser_Id(Integer userId, Pageable pageable);

    Page<TaskEntity> findByStatus(String status, Pageable pageable);

    Page<TaskEntity> findByProject_IdAndStatus(Integer projectId, String status, Pageable pageable);

    Page<TaskEntity> findByAssignedUser_IdAndStatus(Integer userId, String status, Pageable pageable);

    // Non-paginated methods (keep for flexibility)
    List<TaskEntity> findByProject_Id(Integer projectId);

    List<TaskEntity> findByAssignedUser_Id(Integer userId);

    List<TaskEntity> findByStatus(String status);

    List<TaskEntity> findByProject_IdAndStatus(Integer projectId, String status);

    List<TaskEntity> findByAssignedUser_IdAndStatus(Integer userId, String status);

    // Custom queries for better performance
    @Query("SELECT t FROM TaskEntity t LEFT JOIN FETCH t.project LEFT JOIN FETCH t.assignedUser WHERE t.project.id = :projectId")
    Page<TaskEntity> findByProjectIdWithDetails(@Param("projectId") Integer projectId, Pageable pageable);

    @Query("SELECT t FROM TaskEntity t LEFT JOIN FETCH t.project LEFT JOIN FETCH t.assignedUser WHERE t.assignedUser.id = :userId")
    Page<TaskEntity> findByAssignedUserIdWithDetails(@Param("userId") Integer userId, Pageable pageable);

    // Count queries for dashboard/statistics
    long countByStatus(String status);

    long countByProject_Id(Integer projectId);

    long countByAssignedUser_Id(Integer userId);

    @Query("SELECT COUNT(t) FROM TaskEntity t WHERE t.project.team.id = :teamId")
    long countByTeamId(@Param("teamId") Integer teamId);
}