package com.TFG.tempo.controller;

import com.TFG.tempo.data.dtos.PetitionDTOAdd;
import com.TFG.tempo.data.dtos.TeamDTO;
import com.TFG.tempo.data.dtos.TeamDTOAdd;
import com.TFG.tempo.data.entities.Team;
import com.TFG.tempo.data.mapper.TeamMapper;
import com.TFG.tempo.data.service.api.PetitionService;
import com.TFG.tempo.data.service.api.TeamService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @Autowired
  PetitionService petitionService;

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Object> addTeam(@Valid @RequestBody TeamDTOAdd teamDTOAdd) {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        this.getClass().getSimpleName() + " - " +
        new Object() {
        }.getClass().getEnclosingMethod().getName());

    TeamDTO teamDTO = teamMapper.toTeamDTO(teamService.addTeam(teamDTOAdd));

    for (String username : teamDTOAdd.getUsers()) {

      petitionService.addPetition(
          PetitionDTOAdd.builder().
              creator(teamDTOAdd.getOwnerUsername()).
              receiver(username)
              .team(teamDTO.getTeamId())
              .build());
    }

    return new ResponseEntity<>(teamDTO, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "/{userName}", produces = "application/json")
  public ResponseEntity<Object> getTeamsByUserName(@PathVariable("userName") String userName) {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        this.getClass().getSimpleName() + " - " +
        new Object() {
        }.getClass().getEnclosingMethod().getName());


    List<TeamDTO> teamDTOs = new ArrayList<>();
    for (Team team : teamService.findByOwnerUsername(userName)) {
      teamDTOs.add(teamMapper.toTeamDTO(team));
    }

    for (Team team : teamService.findByEmployeesUsername(userName)) {
      teamDTOs.add(teamMapper.toTeamDTO(team));
    }
    return new ResponseEntity<>(teamDTOs, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(value = "addEmployees/{teamId}", produces = "application/json", consumes = "application/json")
  public ResponseEntity<Object> addEmployeesToTeam(@PathVariable("teamId") Long teamId,
                                                   @RequestBody List<String> userNames) {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        this.getClass().getSimpleName() + " - " +
        new Object() {
        }.getClass().getEnclosingMethod().getName());


    return new ResponseEntity<>(teamMapper.toTeamDTO(teamService.addEmployeesToTeam(userNames, teamId)), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @DeleteMapping(value = "/{teamId}", produces = "application/json", consumes = "application/json")
  public ResponseEntity<Object> deleteTeam(@PathVariable("teamId") Long teamId) {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        this.getClass().getSimpleName() + " - " +
        new Object() {
        }.getClass().getEnclosingMethod().getName());


    return new ResponseEntity<>(teamService.deleteTeamById(teamId), HttpStatus.OK);
  }
}
