package com.TFG.tempo.data.mapper;

import com.TFG.tempo.data.dtos.UserDTO;
import com.TFG.tempo.data.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
  public abstract UserDTO toUserDTO(User user);

  public abstract User toUser(UserDTO userDTO);
}
