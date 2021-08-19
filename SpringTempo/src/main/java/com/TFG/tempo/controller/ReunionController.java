package com.TFG.tempo.controller;

import com.TFG.tempo.data.dtos.PetitionDTOAdd;
import com.TFG.tempo.data.dtos.ReunionAddDTO;
import com.TFG.tempo.data.dtos.ReunionDTO;
import com.TFG.tempo.data.entities.Reunion;
import com.TFG.tempo.data.mapper.ReunionMapper;
import com.TFG.tempo.data.service.api.PetitionService;
import com.TFG.tempo.data.service.api.ReunionService;
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
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        this.getClass().getSimpleName() + " - " +
        new Object() {
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

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    SimpleDateFormat formatterDay = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatterHour = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        this.getClass().getSimpleName() + " - " +
        new Object() {
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
      reunionDTOs.add(reunionMapper.toReunionDTO(reunion));
    }
    return new ResponseEntity<>(reunionDTOs, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(value = "/addAssistants/{reunionId}", produces = "application/json", consumes = "application/json")
  public ResponseEntity<Object> addAssistants(@PathVariable("reunionId") Long reunionId,
                                              @RequestBody List<String> userNames) {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        this.getClass().getSimpleName() + " - " +
        new Object() {
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

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    Date date = new Date(System.currentTimeMillis());

    Logger.getGlobal().info("[INFO Controller] ///\\\\\\ Tempo " + formatter.format(date) +
        this.getClass().getSimpleName() + " - " +
        new Object() {
        }.getClass().getEnclosingMethod().getName());


    return new ResponseEntity<>(reunionService.deleteReunion(reunionId), HttpStatus.OK);
  }


}
