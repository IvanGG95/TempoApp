package com.TFG.tempo.data.repository;

import com.TFG.tempo.data.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);

  User findUserByPersonInCharge(Long idPersonInCharge);

  List<User> findUsersByPersonInChargeUsername(String name);
}
