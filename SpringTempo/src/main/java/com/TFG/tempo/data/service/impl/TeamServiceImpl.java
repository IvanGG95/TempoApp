package com.TFG.tempo.data.service.impl;

import com.TFG.tempo.data.dtos.TeamDTOAdd;
import com.TFG.tempo.data.entities.Petition;
import com.TFG.tempo.data.entities.Team;
import com.TFG.tempo.data.entities.User;
import com.TFG.tempo.data.repository.PetitionRepository;
import com.TFG.tempo.data.repository.TeamRepository;
import com.TFG.tempo.data.repository.UserRepository;
import com.TFG.tempo.data.service.api.TeamService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamServiceImpl implements TeamService {

  @Autowired
  TeamRepository teamRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PetitionRepository petitionRepository;

  @Override
  public Team findByOwnerUserId(Long userId) {
    return teamRepository.findByOwnerUserId(userId);
  }

  @Override
  public List<Team> findByEmployeesUserId(Long userId) {
    return teamRepository.findByEmployeesUserId(userId);
  }

  @Override
  public List<Team> findByOwnerUsername(String username) {
    return teamRepository.findByOwnerUsername(username);
  }

  @Override
  public List<Team> findByEmployeesUsername(String username) {
    return teamRepository.findByEmployeesUsername(username);
  }

  @Override
  public List<User> findUsersByTeamId(Long teamId) {
    return teamRepository.findUsersByTeamId(teamId);
  }

  @Override
  public Team addTeam(TeamDTOAdd teamDTOAdd) {
    Team team = new Team();
    team.setDescription(teamDTOAdd.getDescription());
    team.setName(teamDTOAdd.getName());
    team.setOwner(userRepository.findByUsername(teamDTOAdd.getOwnerUsername()));

    return teamRepository.save(team);
  }

  @Override
  @Transactional
  public Team addEmployeesToTeam(List<String> userNames, Long id) {

    Team team = teamRepository.getOne(id);
    List<User> users = new ArrayList<>();
    for (String userName : userNames) {
      users.add(userRepository.findByUsername(userName));
    }

    team.setEmployees(users);

    return team;
  }

  public boolean deleteTeamById(Long id) {

    if (!teamRepository.findById(id).isPresent()) {
      return false;
    }

    List<Petition> petitions = petitionRepository.findByTeamTeamId(id);

    for (Petition petition : petitions) {
      petitionRepository.delete(petition);
    }
    teamRepository.deleteById(id);
    return true;
  }

  @Transactional
  private List<User> getUsers(List<String> usersName) {
    List<User> users = new ArrayList<>();
    for (String userName : usersName) {
      users.add(userRepository.findByUsername(userName));
    }
    return users;
  }


}
