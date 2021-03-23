package com.TFG.tempo.data.service.api;

import com.TFG.tempo.data.entities.User;

public interface UserService {
  User findByUsername(String username);
}
