
package com.project_management_backend.Project.Management.model.mapper;

public interface DtoMapper<E, Req, Res> {
    E convertRequestDtoToEntity(Req requestDto);
    Res convertEntityToResponseDto(E entity);
}
