package com.proj_mngmt.proj_mngmt.model.mapper;

import com.proj_mngmt.proj_mngmt.model.dto.user.UserRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.user.UserResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper extends DtoMapper<UserEntity, UserRequestDTO, UserResponseDTO> {

    @Override
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "id", ignore = true)
    UserEntity convertRequestDtoToEntity(UserRequestDTO requestDto);

    @Override
    @Mapping(target = "teamId", source = "team.id")
    @Mapping(target = "teamName", source = "team.teamName")
    UserResponseDTO convertEntityToResponseDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    void updateUserEntity(@MappingTarget UserEntity userEntity, UserRequestDTO userRequestDTO);
}