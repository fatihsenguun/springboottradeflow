package com.fatihsengun.mapper;

import com.fatihsengun.dto.DtoRegister;
import com.fatihsengun.dto.DtoRegisterUI;
import com.fatihsengun.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IGlobalMapper {
    User toUserEntity(DtoRegisterUI dtoRegisterUI);
    DtoRegister toDtoRegister(User user);

}
