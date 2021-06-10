package com.TFG.tempo.data.service.impl;

import com.TFG.tempo.data.entities.Role;
import com.TFG.tempo.data.repository.RoleRepository;
import com.TFG.tempo.data.service.api.RoleService;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  RoleRepository roleRepository;

  @Named("findRoleByName")
  public Role findByName(String name) {
    return roleRepository.findByName(name);
  }
}
