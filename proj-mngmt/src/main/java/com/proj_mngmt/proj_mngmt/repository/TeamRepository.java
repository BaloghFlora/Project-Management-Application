package com.proj_mngmt.proj_mngmt.repository;

import com.proj_mngmt.proj_mngmt.model.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {

    Optional<TeamEntity> findByTeamName(String teamName);

    boolean existsByTeamName(String teamName);
}