package com.TFG.tempo.controller;

import com.TFG.tempo.data.dtos.ReunionAddDTO;
import com.TFG.tempo.data.service.api.ReunionService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reunion")
public class ReunionController {
  @Autowired
  ReunionService reunionService;

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Object> getAssignedFreeDaysByMonth(@Valid @RequestBody ReunionAddDTO reunionAddDTO) {
    reunionService.addReunion(reunionAddDTO);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
