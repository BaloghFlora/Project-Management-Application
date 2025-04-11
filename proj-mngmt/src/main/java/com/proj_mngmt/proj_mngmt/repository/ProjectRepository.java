package com.proj_mngmt.proj_mngmt.repository;

import com.proj_mngmt.proj_mngmt.model.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {

    List<ProjectEntity> findByTeamId(Integer teamId);

    Optional<ProjectEntity> findByProjectNameAndTeamId(String projectName, Integer teamId);

    boolean existsByProjectNameAndTeamId(String projectName, Integer teamId);
}