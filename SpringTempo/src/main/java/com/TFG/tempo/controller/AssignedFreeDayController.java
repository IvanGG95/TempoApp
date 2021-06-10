package com.TFG.tempo.controller;

import com.TFG.tempo.data.dtos.AssignedFreeDayDTO;
import com.TFG.tempo.data.dtos.AssignedListDTO;
import com.TFG.tempo.data.dtos.DateDTO;
import com.TFG.tempo.data.entities.AssignedFreeDay;
import com.TFG.tempo.data.mapper.AssignedFreeDaysMapper;
import com.TFG.tempo.data.service.api.AssignedFreeDayService;
import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.validation.Valid;
import javax.xml.crypto.Data;
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
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        this.getClass().getSimpleName() + " - " +
        new Object(){}.getClass().getEnclosingMethod().getName());
    
    assignedFreeDayService.addAnAssignedDay(dateDTO.getFirst(), dateDTO.getUserId());
    return new ResponseEntity<>("Todo bene", HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "/byUser/{userId}", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Object> getByUserIdAssignedFreeDays(@PathVariable Long userId) {
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        this.getClass().getSimpleName() + " - " +
        new Object(){}.getClass().getEnclosingMethod().getName());

    List<AssignedFreeDay> assignedDaysByUser = assignedFreeDayService.getAssignedDaysByUser(userId);
    List<AssignedFreeDayDTO> assignedFreeDayDTOS = new ArrayList<>();

    for (AssignedFreeDay assignedFreeDay : assignedDaysByUser) {
      assignedFreeDayDTOS.add(assignedFreeDaysMapper.toAssignedFreeDayDTO(assignedFreeDay));
    }

    return new ResponseEntity<>(assignedFreeDayDTOS, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "/byMonth", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Object> getAssignedFreeDaysByMonth(@Valid @RequestBody DateDTO dateDTO) {

    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        this.getClass().getSimpleName() + " - " +
        new Object(){}.getClass().getEnclosingMethod().getName());

    List<AssignedFreeDay> assignedDaysByUser = assignedFreeDayService.getAssignedDaysByMonth(dateDTO.getFirst());
    List<AssignedFreeDayDTO> assignedFreeDayDTOS = new ArrayList<>();

    for (AssignedFreeDay assignedFreeDay : assignedDaysByUser) {
      assignedFreeDayDTOS.add(assignedFreeDaysMapper.toAssignedFreeDayDTO(assignedFreeDay));
    }

    return new ResponseEntity<>(assignedFreeDayDTOS, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(value = "/listOfDays", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Object> createAnAssignedFreeDay(@Valid @RequestBody AssignedListDTO dateDTOs) throws ParseException {
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        this.getClass().getSimpleName() + " - " +
        new Object(){}.getClass().getEnclosingMethod().getName());

    return new ResponseEntity<>(assignedFreeDayService.addAnAssignedDayList(dateDTOs.getDates()) + "\\n Todo bene", HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @DeleteMapping(value = "/listOfDays", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Object> deleteAssignedFreeDays(@Valid @RequestBody AssignedListDTO dateDTOs) throws ParseException {
    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        this.getClass().getSimpleName() + " - " +
        new Object(){}.getClass().getEnclosingMethod().getName());

      String failedDates = assignedFreeDayService.deleteAssignedDayList(dateDTOs.getDates()).toString();

    return new ResponseEntity<>("Todo bene " + failedDates, HttpStatus.OK);
  }


}
