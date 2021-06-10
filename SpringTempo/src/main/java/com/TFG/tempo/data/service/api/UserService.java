package com.TFG.tempo.data.service.api;

import com.TFG.tempo.data.entities.Role;
import com.TFG.tempo.data.entities.User;
import java.util.Collection;
import java.util.List;
import org.mapstruct.Named;

public interface UserService {
  User findByUsername(String username);

  User saveUser(User user);

  User findUserByPersonInCharge(Long idPersonInCharge);

  @Named("findById")
  User findById(Long id);

  Collection<Role> findRoleByUserId(Long id);


  List<User> findUsersByPersonInChargeName(String name);
}
