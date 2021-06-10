package com.TFG.tempo.controller;

import com.TFG.tempo.data.dtos.MonthDTO;
import com.TFG.tempo.data.service.api.AssignedFreeDayService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/month")
public class MonthController {

  @Autowired
  AssignedFreeDayService assignedFreeDayService;

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<Object> getAssignedFreeDaysByMonth(@Valid @RequestBody MonthDTO monthDTO) {
    return new ResponseEntity<>(assignedFreeDayService.getMonthsCalendar(monthDTO.getDate(), monthDTO.getUserName()), HttpStatus.OK);
  }

}
