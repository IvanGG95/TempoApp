package com.TFG.tempo.controller;

import static com.TFG.tempo.ControllerLogger.logMethod;


import com.TFG.tempo.data.dtos.RoleDTO;
import com.TFG.tempo.data.dtos.UserDTO;
import com.TFG.tempo.data.entities.Role;
import com.TFG.tempo.data.entities.User;
import com.TFG.tempo.data.mapper.UserMapper;
import com.TFG.tempo.data.service.api.RoleService;
import com.TFG.tempo.data.service.api.TeamService;
import com.TFG.tempo.data.service.api.UserService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
  @Autowired
  UserService userService;

  @Autowired
  RoleService roleService;

  @Autowired
  TeamService teamService;

  @Autowired
  UserMapper userMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<Object> createUser(@Valid @RequestBody UserDTO userDTO) throws ParseException {
    logMethod(this.getClass().getSimpleName(), new Object() {
    }.getClass().getEnclosingMethod().getName());

    if (userService.findByUsername(userDTO.getUsername()) != null) {
      return new ResponseEntity<>("[\"Nombre de usuario en uso " + userDTO.getUsername() + "\"]",
          HttpStatus.BAD_REQUEST);
    }

    if (userDTO.getPersonInChargeId() == null) {
      userDTO.setPersonInChargeId(userService.findByUsername("Admin").getUserId());
    }
    if (userDTO.getRoles() == null) {
      userDTO.setRoles(new ArrayList<>());
      userDTO.getRoles().add(RoleDTO.builder().name("ROLE_USER").build());
    }
    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    User user = userMapper.toUser(userDTO);
    Collection<Role> roles = new ArrayList<>();

    for (RoleDTO roleDTO : userDTO.getRoles()) {
      roles.add(roleService.findByName(roleDTO.getName()));
    }

    user.setRoles(roles);
    userService.saveUser(user);

    return new ResponseEntity<>("{ \"msn\": \" Todo bene \"}", HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "/{userName}", produces = "application/json")
  public ResponseEntity<Object> getUserByUsername(@PathVariable String userName) {
    logMethod(this.getClass().getSimpleName(), new Object() {
    }.getClass().getEnclosingMethod().getName());
    return new ResponseEntity<>(userMapper.toUserDTO(userService.findByUsername(userName)), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "workers/", produces = "application/json")
  public ResponseEntity<Object> getWorkersByUsername(@RequestParam String userName) {
    logMethod(this.getClass().getSimpleName(), new Object() {
    }.getClass().getEnclosingMethod().getName());

    return new ResponseEntity<>(userMapper.toUsersDto(userService.findUsersByPersonInChargeName(userName)),
        HttpStatus.OK);
  }

  @GetMapping(value = "authentication", produces = "application/json")
  public ResponseEntity<Object> getAuthentication(@RequestParam String userName) {
    logMethod(this.getClass().getSimpleName(), new Object() {
    }.getClass().getEnclosingMethod().getName());

    User user = userService.findByUsername(userName);
    if (user == null) {
      return new ResponseEntity<>("User not exists", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(userMapper.toUserDTO(user), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "/byName/{userName}", produces = "application/json")
  public ResponseEntity<Object> getUsersByUsername(@PathVariable String userName) {
    logMethod(this.getClass().getSimpleName(), new Object() {
    }.getClass().getEnclosingMethod().getName());

    return new ResponseEntity<>(userMapper.toUsersDto(userService.findByUsernameContaining(userName)), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "/getAll", produces = "application/json")
  public ResponseEntity<Object> getAllUsers() {
    logMethod(this.getClass().getSimpleName(), new Object() {
    }.getClass().getEnclosingMethod().getName());
    return new ResponseEntity<>(userMapper.toUsersDto(userService.findAll()), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "/getUsersByTeam/{teamId}", produces = "application/json")
  public ResponseEntity<Object> getUsersByTeams(@PathVariable Long teamId) {
    logMethod(this.getClass().getSimpleName(), new Object() {
    }.getClass().getEnclosingMethod().getName());
    List<UserDTO> userDTOList = teamService.findUsersByTeamId(teamId).stream()
        .map(user -> userMapper.toUserDTO(user)).collect(
            Collectors.toList());
    return new ResponseEntity<>(userDTOList, HttpStatus.OK);
  }
}
