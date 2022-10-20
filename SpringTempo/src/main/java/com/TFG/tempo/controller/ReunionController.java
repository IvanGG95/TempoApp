package com.TFG.tempo.controller;

import static com.TFG.tempo.ControllerLogger.logMethod;


import com.TFG.tempo.data.dtos.PetitionDTOAdd;
import com.TFG.tempo.data.dtos.ReunionAddDTO;
import com.TFG.tempo.data.dtos.ReunionDTO;
import com.TFG.tempo.data.entities.Reunion;
import com.TFG.tempo.data.mapper.ReunionMapper;
import com.TFG.tempo.data.service.api.PetitionService;
import com.TFG.tempo.data.service.api.ReunionService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/reunion")
public class ReunionController {
  @Autowired
  ReunionService reunionService;

  @Autowired
  PetitionService petitionService;

  @Autowired
  ReunionMapper reunionMapper;

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Object> addReunion(@Valid @RequestBody ReunionAddDTO reunionAddDTO) {
    logMethod(this.getClass().getSimpleName(), new Object() {
    }.getClass().getEnclosingMethod().getName());
    ReunionDTO reunion = reunionMapper.toReunionDTO(reunionService.addReunion(reunionAddDTO));
    for (String userName : reunionAddDTO.getUsers()) {
      petitionService.addPetition(
          PetitionDTOAdd.builder().
              creator(reunionAddDTO.getCreator()).
              receiver(userName)
              .reunion(reunion.getReunionId())
              .build());
    }
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "/{userName}", produces = "application/json")
  public ResponseEntity<Object> getReunionByUserName(@PathVariable("userName") String userName) {
    SimpleDateFormat formatterDay = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatterHour = new SimpleDateFormat("HH:mm:ss");
    logMethod(this.getClass().getSimpleName(), new Object() {
    }.getClass().getEnclosingMethod().getName());

    List<ReunionDTO> reunionDTOs = new ArrayList<>();
    for (Reunion reunion : reunionService.findByCreatorUserUsername(userName)) {
      ReunionDTO reunionDTO = reunionMapper.toReunionDTO(reunion);
      reunionDTO.setDays(formatterDay.format(reunionDTO.getDate()));
      reunionDTO.setHours(formatterHour.format(reunionDTO.getDate()));
      reunionDTOs.add(reunionDTO);
    }
    for (Reunion reunion : reunionService.findByAssistantUsername(userName)) {
      ReunionDTO reunionDTO = reunionMapper.toReunionDTO(reunion);
      reunionDTO.setDays(formatterDay.format(reunionDTO.getDate()));
      reunionDTO.setHours(formatterHour.format(reunionDTO.getDate()));
      reunionDTOs.add(reunionDTO);
    }
    return new ResponseEntity<>(reunionDTOs, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(value = "/addAssistants/{reunionId}", produces = "application/json", consumes = "application/json")
  public ResponseEntity<Object> addAssistants(@PathVariable("reunionId") Long reunionId,
                                              @RequestBody List<String> userNames) {
    logMethod(this.getClass().getSimpleName(), new Object() {
    }.getClass().getEnclosingMethod().getName());
    Reunion reunion = reunionService.findById(reunionId);
    for (String name : userNames) {
      petitionService.addPetition(
          PetitionDTOAdd.builder()
              .status("Pendiente")
              .receiver(name)
              .creator(reunion.getCreator().getUsername())
              .reunion(reunionId)
              .build());
    }

    return new ResponseEntity<>(reunionMapper.toReunionDTO(reunion), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @DeleteMapping(value = "/{reunionId}", produces = "application/json", consumes = "application/json")
  public ResponseEntity<Object> deleteReunion(@PathVariable("reunionId") Long reunionId) {
    logMethod(this.getClass().getSimpleName(), new Object() {
    }.getClass().getEnclosingMethod().getName());
    return new ResponseEntity<>(reunionService.deleteReunion(reunionId), HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(value = "exitReunion/{reunionId}/{username}", produces = "application/json")
  public ResponseEntity<Object> exitReunion(@PathVariable("reunionId") Long teamId,
                                            @PathVariable("username") String username) {
    logMethod(this.getClass().getSimpleName(), new Object() {
    }.getClass().getEnclosingMethod().getName());
    if (!reunionService.exitReunion(teamId, username)) {
      return new ResponseEntity<>("[\"El usuario no pudo ser elminado de la reunion\"]", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>("[\"Todo Bene\"]", HttpStatus.OK);
  }


}
