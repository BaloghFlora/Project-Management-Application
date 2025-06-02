package com.proj_mngmt.proj_mngmt.service.user;

import com.proj_mngmt.proj_mngmt.exception.model.DataNotFoundException;
import com.proj_mngmt.proj_mngmt.exception.model.ExceptionCode;
import com.proj_mngmt.proj_mngmt.model.dto.user.UserResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.UserEntity;
import com.proj_mngmt.proj_mngmt.model.mapper.UserMapper;
import com.proj_mngmt.proj_mngmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceBean implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserResponseDTO findByEmail(String email) {
        log.debug("Finding user by email: {}", email);

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new DataNotFoundException(ExceptionCode.USER_NOT_FOUND, email);
                });

        // Debug logging to check team data
        log.debug("Found user: {} with team: {}", user.getName(),
                user.getTeam() != null ? user.getTeam().getTeamName() : "NO TEAM");

        UserResponseDTO response = userMapper.convertEntityToResponseDto(user);
        log.debug("Mapped response - teamId: {}, teamName: {}", response.teamId(), response.teamName());

        return response;
    }

    @Override
    public UserResponseDTO findById(Integer id) {
        log.debug("Finding user by id: {}", id);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new DataNotFoundException(ExceptionCode.USER_NOT_FOUND, id);
                });

        // Debug logging to check team data
        log.debug("Found user: {} with team: {}", user.getName(),
                user.getTeam() != null ? user.getTeam().getTeamName() : "NO TEAM");

        UserResponseDTO response = userMapper.convertEntityToResponseDto(user);
        log.debug("Mapped response - teamId: {}, teamName: {}", response.teamId(), response.teamName());

        return response;
    }
}