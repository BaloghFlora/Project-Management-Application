package com.project_management_backend.Project.Management.repository;

import java.util.Optional;

import com.project_management_backend.Project.Management.model.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {

    Optional<TeamEntity> findByTeamName(String teamName);

    boolean existsByTeamName(String teamName);
}