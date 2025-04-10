package com.project_management_backend.Project.Management.repository;

import java.util.List;
import java.util.Optional;

import com.project_management_backend.Project.Management.model.entity.UserEntity;
import com.project_management_backend.Project.Management.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByTeamId(Integer teamId);

    List<UserEntity> findByRole(Role role);

    boolean existsByEmail(String email);
}