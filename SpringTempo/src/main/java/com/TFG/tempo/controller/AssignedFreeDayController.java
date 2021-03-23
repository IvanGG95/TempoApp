package com.TFG.tempo.controller;

import com.TFG.tempo.data.dtos.AssignedFreeDayDTO;
import com.TFG.tempo.data.dtos.DateDTO;
import com.TFG.tempo.data.entities.AssignedFreeDay;
import com.TFG.tempo.data.mapper.AssignedFreeDaysMapper;
import com.TFG.tempo.data.service.api.AssignedFreeDayService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
@RequestMapping("/assignedFreeDay")
public class AssignedFreeDayController {

  @Autowired
  AssignedFreeDayService assignedFreeDayService;

  @Autowired
  AssignedFreeDaysMapper assignedFreeDaysMapper;

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<Object> createAnAssignedFreeDay(@Valid @RequestBody DateDTO dateDTO) throws ParseException {
    assignedFreeDayService.addAnAssignedDay(dateDTO.getFirst(), dateDTO.getUserId());
    return new ResponseEntity<>("Todo bene", HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(value = "/listOfDays", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Object> createAssignedFreeDays(@Valid @RequestBody DateDTO dateDTO) {
    assignedFreeDayService.addAssignedDays(dateDTO.getFirst(), dateDTO.getSecond(), dateDTO.getUserId());
    return new ResponseEntity<>("Todo bene", HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @DeleteMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<Object> deleteAnAssignedFreeDay(@Valid @RequestBody DateDTO dateDTO) throws ParseException {
    assignedFreeDayService.deleteAssignedDay(dateDTO.getFirst(), dateDTO.getUserId());
    return new ResponseEntity<>("Todo bene", HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @DeleteMapping(value = "/listOfDays", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Object> deleteAssignedFreeDays(@Valid @RequestBody DateDTO dateDTO) throws ParseException {
    assignedFreeDayService.deleteAssignedDays(dateDTO.getFirst(), dateDTO.getSecond(), dateDTO.getUserId());
    return new ResponseEntity<>("Todo bene", HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "/byUser/{userId}", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Object> getByUserIdAssignedFreeDays(@PathVariable Long userId) {
    List<AssignedFreeDay> assignedDaysByUser = assignedFreeDayService.getAssignedDaysByUser(userId);
    List<AssignedFreeDayDTO> assignedFreeDayDTOS = new ArrayList<>();

    for (AssignedFreeDay assignedFreeDay : assignedDaysByUser) {
      assignedFreeDayDTOS.add(assignedFreeDaysMapper.toAssignedFreeDayDTO(assignedFreeDay));
    }

    return new ResponseEntity<>(assignedFreeDayDTOS, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('USER')")
  @GetMapping(value = "/byMonth", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Object> getAssignedFreeDaysByMonth(@Valid @RequestBody DateDTO dateDTO) {
    List<AssignedFreeDay> assignedDaysByUser = assignedFreeDayService.getAssignedDaysByMonth(dateDTO.getFirst());
    List<AssignedFreeDayDTO> assignedFreeDayDTOS = new ArrayList<>();

    for (AssignedFreeDay assignedFreeDay : assignedDaysByUser) {
      assignedFreeDayDTOS.add(assignedFreeDaysMapper.toAssignedFreeDayDTO(assignedFreeDay));
    }

    return new ResponseEntity<>(assignedFreeDayDTOS, HttpStatus.OK);
  }


}
