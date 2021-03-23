package com.TFG.tempo.data.service.impl;

import com.TFG.tempo.data.entities.User;
import com.TFG.tempo.data.repository.UserRepository;
import com.TFG.tempo.data.service.api.UserService;
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
}
