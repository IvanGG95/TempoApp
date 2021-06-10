package com.TFG.tempo.data.mapper;

import com.TFG.tempo.data.dtos.RoleDTO;
import com.TFG.tempo.data.entities.Role;
import com.TFG.tempo.data.service.api.RoleService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleService.class})
public abstract class RoleMapper {

  public abstract RoleDTO toRoleDTO(Role role);

  public abstract Role toRole(RoleDTO roleDTO);
}
