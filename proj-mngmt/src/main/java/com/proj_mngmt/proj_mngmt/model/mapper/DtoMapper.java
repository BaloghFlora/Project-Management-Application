
package com.proj_mngmt.proj_mngmt.model.mapper;

public interface DtoMapper<E, Req, Res> {
    E convertRequestDtoToEntity(Req requestDto);
    Res convertEntityToResponseDto(E entity);
}
