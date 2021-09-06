package com.TFG.tempo.controller;

import com.TFG.tempo.data.dtos.MonthDTO;
import com.TFG.tempo.data.service.api.AssignedFreeDayService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/month")
public class MonthController {

  @Autowired
  AssignedFreeDayService assignedFreeDayService;

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<Object> getAssignedFreeDaysByMonth(@Valid @RequestBody MonthDTO monthDTO) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        this.getClass().getSimpleName() + " - " +
        new Object() {
        }.getClass().getEnclosingMethod().getName());

    return new ResponseEntity<>(assignedFreeDayService.getMonthsCalendar(monthDTO.getDate(), monthDTO.getUserName()),
        HttpStatus.OK);
  }

}
