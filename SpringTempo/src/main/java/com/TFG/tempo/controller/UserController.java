package com.TFG.tempo.controller;

import com.TFG.tempo.data.dtos.RoleDTO;
import com.TFG.tempo.data.dtos.UserDTO;
import com.TFG.tempo.data.entities.Role;
import com.TFG.tempo.data.entities.User;
import com.TFG.tempo.data.mapper.UserMapper;
import com.TFG.tempo.data.service.api.RoleService;
import com.TFG.tempo.data.service.api.UserService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  UserService userService;

  @Autowired
  RoleService roleService;

  @Autowired
  UserMapper userMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<Object> createUser(@Valid @RequestBody UserDTO userDTO) throws ParseException {

    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    User user = userMapper.toUser(userDTO);
    Collection<Role> roles = new ArrayList<>();

    for (RoleDTO roleDTO : userDTO.getRoles()) {
      roles.add(roleService.findByName(roleDTO.getName()));
    }

    user.setRoles(roles);
    userService.saveUser(user);

    return new ResponseEntity<>("Todo bene", HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "/{userName}", produces = "application/json")
  public ResponseEntity<Object> getUserByUsername(@PathVariable String userName) {
    return new ResponseEntity<>(userMapper.toUserDTO(userService.findByUsername(userName)), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "workers/", produces = "application/json")
  public ResponseEntity<Object> getWorkersByUsername(@RequestParam String userName) {
    List<UserDTO> userDTOList = new ArrayList<>();
    for (User user : userService.findUsersByPersonInChargeName(userName)) {
      userDTOList.add(userMapper.toUserDTO(user));
    }
    return new ResponseEntity<>(userDTOList, HttpStatus.OK);
  }

  @GetMapping(value = "authentication", produces = "application/json")
  public ResponseEntity<Object> getAuthentication(@RequestParam String userName) {
    User user = userService.findByUsername(userName);
    if (user == null) {
      return new ResponseEntity<>("User not exists", HttpStatus.BAD_REQUEST);
    }

    UserDTO userNoPass = userMapper.toUserDTO(user);
    userNoPass.setPassword(null);

    return new ResponseEntity<>(userNoPass, HttpStatus.OK);
  }
}
