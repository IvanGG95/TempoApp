package com.TFG.tempo.data.mapper;

import com.TFG.tempo.data.dtos.UserDTO;
import com.TFG.tempo.data.entities.User;
import com.TFG.tempo.data.service.api.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {UserService.class})
public abstract class UserMapper {
  public abstract UserDTO toUserDTO(User user);

  @Mappings({
      @Mapping(target = "personInCharge", source = "personInChargeId", qualifiedByName = "findById")
  })
  public abstract User toUser(UserDTO userDTO);
}
