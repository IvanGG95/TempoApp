package com.TFG.tempo.data.service.impl;

import com.TFG.tempo.data.entities.Role;
import com.TFG.tempo.data.entities.User;
import com.TFG.tempo.data.repository.UserRepository;
import com.TFG.tempo.data.service.api.UserService;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public User findUserByPersonInCharge(Long idPersonInCharge) {
    return userRepository.findUserByPersonInCharge(idPersonInCharge);
  }

  @Named("findById")
  @Override
  public User findById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @Named("findRoleByUserId")
  @Override
  public Collection<Role> findRoleByUserId(Long id) {
    Optional<User> user = userRepository.findById(id);
    return user.map(User::getRoles).orElse(null);
  }

  @Override
  public List<User> findUsersByPersonInChargeName(String name) {
    if (findByUsername(name) != null) {
      return userRepository.findUsersByPersonInChargeUsername(name);
    }
    return null;
  }
}
