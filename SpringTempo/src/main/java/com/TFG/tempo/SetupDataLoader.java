package com.TFG.tempo;

import com.TFG.tempo.data.entities.Role;
import com.TFG.tempo.data.entities.User;
import com.TFG.tempo.data.repository.RoleRepository;
import com.TFG.tempo.data.repository.UserRepository;
import java.util.Collections;
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
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (alreadySetup) {
      return;
    }

    createRoleIfNotFound("ROLE_ADMIN");
    createRoleIfNotFound("ROLE_USER");
    createAdminIfNotExists();
    createUserIfNotExists("");
    createUserIfNotExists("1");
    createUserIfNotExists("2");

    alreadySetup = true;
  }

  void createAdminIfNotExists() {
    Role adminRole = roleRepository.findByName("ROLE_ADMIN");
    User admin = new User();
    admin.setUsername("Admin");
    admin.setPassword(passwordEncoder().encode("Admin"));
    admin.setEmail("Admin@Admin.com");
    admin.setRoles(Collections.singletonList(adminRole));
    admin.setEnabled(true);
    admin.setAvailableFreeDays(22);
    admin.setTotalFreeDays(22);
    admin.setWeeklyWorkHours(42);
    createUserIfNotFound(admin);
  }

  void createUserIfNotExists(String num) {
    Role userRole = roleRepository.findByName("ROLE_USER");
    User admin = new User();
    admin.setUsername("User" + num);
    admin.setPassword(passwordEncoder().encode("User" + num));
    admin.setEmail("User" + num + "@User.com");
    admin.setRoles(Collections.singletonList(userRole));
    admin.setEnabled(true);
    admin.setAvailableFreeDays(22);
    admin.setTotalFreeDays(22);
    admin.setWeeklyWorkHours(42);
    admin.setPersonInCharge(userRepository.findByUsername("Admin"));
    createUserIfNotFound(admin);
  }

  @Transactional
  void createRoleIfNotFound(
      String name) {

    Role role = roleRepository.findByName(name);
    if (role == null) {
      role = new Role(name);
      roleRepository.save(role);
    }
  }

  @Transactional
  void createUserIfNotFound(User admin) {
    User getUser = userRepository.findByUsername(admin.getUsername());
    if (getUser == null) {
      userRepository.save(admin);
    }
  }
}