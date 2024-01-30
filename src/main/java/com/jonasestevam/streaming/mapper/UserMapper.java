package com.jonasestevam.streaming.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.jonasestevam.streaming.dto.UserDTO;
import com.jonasestevam.streaming.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDTO, User> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
