package com.project_management_backend.Project.Management.repository;

import java.util.List;
import java.util.Optional;

import com.project_management_backend.Project.Management.model.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {

    List<ProjectEntity> findByTeamId(Integer teamId);

    Optional<ProjectEntity> findByProjectNameAndTeamId(String projectName, Integer teamId);

    boolean existsByProjectNameAndTeamId(String projectName, Integer teamId);
}