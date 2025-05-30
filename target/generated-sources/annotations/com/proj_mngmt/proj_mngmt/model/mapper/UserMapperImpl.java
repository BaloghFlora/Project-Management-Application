package com.proj_mngmt.proj_mngmt.model.mapper;

import com.proj_mngmt.proj_mngmt.model.dto.user.UserRequestDTO;
import com.proj_mngmt.proj_mngmt.model.dto.user.UserResponseDTO;
import com.proj_mngmt.proj_mngmt.model.entity.Role;
import com.proj_mngmt.proj_mngmt.model.entity.TeamEntity;
import com.proj_mngmt.proj_mngmt.model.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-23T15:24:03+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity convertRequestDtoToEntity(UserRequestDTO requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setName( requestDto.name() );
        userEntity.setEmail( requestDto.email() );
        userEntity.setPassword( requestDto.password() );
        userEntity.setRole( requestDto.role() );

        return userEntity;
    }

    @Override
    public UserResponseDTO convertEntityToResponseDto(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Integer teamId = null;
        String teamName = null;
        Integer id = null;
        String name = null;
        String email = null;
        Role role = null;

        teamId = entityTeamId( entity );
        teamName = entityTeamTeamName( entity );
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        role = entity.getRole();

        UserResponseDTO userResponseDTO = new UserResponseDTO( id, name, email, role, teamId, teamName );

        return userResponseDTO;
    }

    @Override
    public void updateUserEntity(UserEntity userEntity, UserRequestDTO userRequestDTO) {
        if ( userRequestDTO == null ) {
            return;
        }

        userEntity.setName( userRequestDTO.name() );
        userEntity.setEmail( userRequestDTO.email() );
        userEntity.setPassword( userRequestDTO.password() );
        userEntity.setRole( userRequestDTO.role() );
    }

    private Integer entityTeamId(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }
        TeamEntity team = userEntity.getTeam();
        if ( team == null ) {
            return null;
        }
        Integer id = team.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityTeamTeamName(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }
        TeamEntity team = userEntity.getTeam();
        if ( team == null ) {
            return null;
        }
        String teamName = team.getTeamName();
        if ( teamName == null ) {
            return null;
        }
        return teamName;
    }
}
