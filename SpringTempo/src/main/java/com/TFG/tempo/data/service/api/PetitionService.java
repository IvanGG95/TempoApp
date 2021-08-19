package com.TFG.tempo.data.service.api;

import com.TFG.tempo.data.dtos.PetitionDTOAdd;
import com.TFG.tempo.data.entities.Petition;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface PetitionService {

  Petition addPetition(PetitionDTOAdd petitionDTO);

  @Transactional
  Petition updatePetition(Long petitionId, String status);

  List<Petition> findByCreatorUserId(Long userId);

  List<Petition> findByReceiverUserId(Long userId);

  List<Petition> findByReceiverUserIdAndStatus(Long userId, String status);

  List<Petition> findByCreatorUserIdAndStatus(Long userId, String status);

  boolean deletePetition(Long id);
}
