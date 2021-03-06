package com.TFG.tempo.controller;

import com.TFG.tempo.data.dtos.PetitionDTO;
import com.TFG.tempo.data.dtos.PetitionDTOAdd;
import com.TFG.tempo.data.dtos.PetitionUpdateDTO;
import com.TFG.tempo.data.entities.Petition;
import com.TFG.tempo.data.entities.User;
import com.TFG.tempo.data.mapper.PetitionMapper;
import com.TFG.tempo.data.service.api.PetitionService;
import com.TFG.tempo.data.service.api.UserService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/petition")
public class PetitionController {
  @Autowired
  PetitionService petitionService;

  @Autowired
  PetitionMapper petitionMapper;

  @Autowired
  UserService userService;

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Object> addPetition(@Valid @RequestBody PetitionDTOAdd petitionDTOAdd) {
    return new ResponseEntity<>(petitionMapper.toPetitionDTO(petitionService.addPetition(petitionDTOAdd)),
        HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "/owner/{userName}", produces = "application/json")
  public ResponseEntity<Object> getPetitionsByOwnerName(@PathVariable("userName") String userName) {
    List<PetitionDTO> petitionDTOS = new ArrayList<>();
    User user = userService.findByUsername(userName);
    for (Petition petition : petitionService.findByCreatorUserId(user.getUserId())) {
      petitionDTOS.add(petitionMapper.toPetitionDTO(petition));
    }
    return new ResponseEntity<>(petitionDTOS, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @GetMapping(value = "/receiver/{userName}", produces = "application/json")
  public ResponseEntity<Object> getPetitionsByReceiverName(@PathVariable("userName") String userName) {
    List<PetitionDTO> petitionDTOS = new ArrayList<>();
    User user = userService.findByUsername(userName);
    for (Petition petition : petitionService.findByReceiverUserId(user.getUserId())) {
      petitionDTOS.add(petitionMapper.toPetitionDTO(petition));
    }
    return new ResponseEntity<>(petitionDTOS, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
  @PutMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Object> updatePetition(@Valid @RequestBody PetitionUpdateDTO petitionUpdateDTO) {
    return new ResponseEntity<>(
        petitionMapper.toPetitionDTO(petitionService.updatePetition(petitionUpdateDTO.getPetitionId(),
            petitionUpdateDTO.getStatus())),
        HttpStatus.OK);
  }

}
