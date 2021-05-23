package com.TFG.tempo.data.service.api;

import com.TFG.tempo.data.entities.Role;
import org.mapstruct.Named;

public interface RoleService {

  @Named("findRoleByName")
  Role findByName(String name);
}
