package com.proj_mngmt.proj_mngmt.repository;

import com.proj_mngmt.proj_mngmt.model.entity.Role;
import com.proj_mngmt.proj_mngmt.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByTeamId(Integer teamId);

    List<UserEntity> findByRole(Role role);

    boolean existsByEmail(String email);
}