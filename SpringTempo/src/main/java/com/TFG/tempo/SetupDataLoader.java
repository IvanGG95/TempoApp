package com.TFG.tempo;

import com.TFG.tempo.data.entities.Role;
import com.TFG.tempo.data.entities.User;
import com.TFG.tempo.data.repository.RoleRepository;
import com.TFG.tempo.data.repository.UserRepository;
import java.util.Arrays;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SetupDataLoader implements
    ApplicationListener<ContextRefreshedEvent> {

  boolean alreadySetup = false;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (alreadySetup) {
      return;
    }

    createRoleIfNotFound("ROLE_ADMIN");
    createRoleIfNotFound("ROLE_USER");

    Role adminRole = roleRepository.findByName("ROLE_ADMIN");
    User user = new User();
    user.setUsername("Test");
    user.setPassword(passwordEncoder().encode("test"));
    user.setEmail("test@test.com");
    user.setRoles(Arrays.asList(adminRole));
    user.setEnabled(true);
    createUserIfNotFound(user);

    alreadySetup = true;
  }

  @Transactional
  Role createRoleIfNotFound(
      String name) {

    Role role = roleRepository.findByName(name);
    if (role == null) {
      role = new Role(name);
      roleRepository.save(role);
    }
    return role;
  }

  @Transactional
  void createUserIfNotFound(User user) {
    User getUser = userRepository.findByUsername(user.getUsername());
    if (getUser == null) {
      userRepository.save(user);
    }
  }
}