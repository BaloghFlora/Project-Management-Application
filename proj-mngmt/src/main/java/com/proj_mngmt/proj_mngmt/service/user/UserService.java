package com.proj_mngmt.proj_mngmt.service.user;


import com.proj_mngmt.proj_mngmt.model.dto.user.UserResponseDTO;

public interface UserService {
    UserResponseDTO findByEmail(String email);
    UserResponseDTO findById(Integer id);
}
