package com.proj_mngmt.proj_mngmt.repository;

import com.proj_mngmt.proj_mngmt.model.entity.Role;
import com.proj_mngmt.proj_mngmt.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.team.id = :teamId")
    Page<UserEntity> findUsersByTeamId(@Param("teamId") Integer teamId, Pageable pageable);
    @Query("SELECT u FROM UserEntity u WHERE u.team.id = :teamId")
    List<UserEntity> findUsersByTeamId(@Param("teamId") Integer teamId);
    List<UserEntity> findByTeamId(Integer teamId, Pageable pageable);
    Page<UserEntity> findByTeamIsNull(Pageable pageable);
    List<UserEntity> findByRole(Role role);

    boolean existsByEmail(String email);
}