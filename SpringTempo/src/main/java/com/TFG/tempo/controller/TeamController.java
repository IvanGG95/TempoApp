package com.TFG.tempo.controller;

import com.TFG.tempo.data.dtos.TeamDTO;
import com.TFG.tempo.data.dtos.TeamDTOAdd;
import com.TFG.tempo.data.entities.Team;
import com.TFG.tempo.data.mapper.TeamMapper;
import com.TFG.tempo.data.service.api.TeamService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
public class TeamController {

  @Autowired
  TeamService teamService;

  @Autowired
  TeamMapper teamMapper;

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Object> addTeam(@Valid @RequestBody TeamDTOAdd teamDTOAdd) {
    return new ResponseEntity<>(teamMapper.toTeamDTO(teamService.addTeam(teamDTOAdd)), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "/{userName}", produces = "application/json")
  public ResponseEntity<Object> getTeamsByUserName(@PathVariable("userName") String userName) {
    List<TeamDTO> teamDTOs = new ArrayList<>();
    for (Team team : teamService.findByOwnerUsername(userName)) {
      teamDTOs.add(teamMapper.toTeamDTO(team));
    }
    return new ResponseEntity<>(teamDTOs, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(value = "addEmployees/{teamId}", produces = "application/json", consumes = "application/json")
  public ResponseEntity<Object> addEmployeesToTeam(@PathVariable("teamId") Long teamId,
                                                   @RequestBody List<String> userNames) {
    return new ResponseEntity<>(teamMapper.toTeamDTO(teamService.addEmployeesToTeam(userNames, teamId)), HttpStatus.OK);
  }
}
