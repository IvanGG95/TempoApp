package com.TFG.tempo.data.service.impl;

import com.TFG.tempo.data.dtos.PetitionDTOAdd;
import com.TFG.tempo.data.entities.Petition;
import com.TFG.tempo.data.entities.Reunion;
import com.TFG.tempo.data.entities.Team;
import com.TFG.tempo.data.entities.User;
import com.TFG.tempo.data.repository.PetitionRepository;
import com.TFG.tempo.data.repository.ReunionRepository;
import com.TFG.tempo.data.repository.TeamRepository;
import com.TFG.tempo.data.repository.UserRepository;
import com.TFG.tempo.data.service.api.PetitionService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetitionServiceImpl implements PetitionService {

  @Autowired
  PetitionRepository petitionRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ReunionRepository reunionRepository;

  @Autowired
  TeamRepository teamRepository;

  @Override
  @Transactional
  public Petition addPetition(PetitionDTOAdd petitionDTO) {
    Petition petition = new Petition();

    User creator = userRepository.findByUsername(petitionDTO.getCreator());
    User receiver = userRepository.findByUsername(petitionDTO.getReceiver());
    Reunion reunion = null;
    Team team = null;

    if (petitionDTO.getReunion() != null) {
      if (reunionRepository.findById(petitionDTO.getReunion()).isPresent()) {
        reunion = reunionRepository.findById(petitionDTO.getReunion()).get();
      }
    }

    if (petitionDTO.getTeam() != null) {
      if (teamRepository.findById(petitionDTO.getTeam()).isPresent()) {
        team = teamRepository.findById(petitionDTO.getTeam()).get();
      }
    }

    petition.setCreator(creator);
    petition.setReceiver(receiver);
    petition.setReunion(reunion);
    petition.setTeam(team);
    petition.setStatus("Pendiente");
    return petitionRepository.save(petition);
  }

  @Override
  @Transactional
  public Petition updatePetition(Long petitionId, String status) {

    Optional<Petition> petition = petitionRepository.findById(petitionId);
    if (petition.isPresent()) {
      petition.get().setStatus(status);
      return petition.get();
    }

    return null;
  }

  @Override
  public List<Petition> findByCreatorUserId(Long userId) {
    return petitionRepository.findByCreatorUserId(userId);
  }

  @Override
  public List<Petition> findByReceiverUserId(Long userId) {
    return petitionRepository.findByReceiverUserId(userId);
  }

  @Override
  public List<Petition> findByReceiverUserIdAndStatus(Long userId, String status) {
    return petitionRepository.findByReceiverUserIdAndStatus(userId, status);
  }

  @Override
  public List<Petition> findByCreatorUserIdAndStatus(Long userId, String status) {
    return petitionRepository.findByCreatorUserIdAndStatus(userId, status);
  }

  @Override
  public boolean deletePetition(Long id) {

    petitionRepository.deleteById(id);
    return !petitionRepository.findById(id).isPresent();

  }
}
