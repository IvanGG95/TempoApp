package com.TFG.tempo.data.service.api;

import com.TFG.tempo.data.dtos.TeamDTOAdd;
import com.TFG.tempo.data.entities.Team;
import com.TFG.tempo.data.entities.User;
import java.util.List;

public interface TeamService {

  Team findByOwnerUserId(Long userId);

  List<Team> findByEmployeesUserId(Long userId);

  List<Team> findByOwnerUsername(String username);

  List<Team> findByEmployeesUsername(String username);

  List<User> findUsersByTeamId(Long teamId);

  Team addTeam(TeamDTOAdd teamDTOAdd);

  Team addEmployeesToTeam(List<String> userNames, Long id);
}
