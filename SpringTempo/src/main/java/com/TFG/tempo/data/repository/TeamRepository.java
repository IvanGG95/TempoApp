package com.TFG.tempo.data.repository;

import com.TFG.tempo.data.entities.Team;
import com.TFG.tempo.data.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

  Team findByOwnerUserId(Long userId);

  List<Team> findByEmployeesUserId(Long userId);

  List<Team> findByOwnerUsername(String username);

  List<Team> findByEmployeesUsername(String username);

  List<User> findUsersByTeamId(Long teamId);
}
