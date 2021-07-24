package com.TFG.tempo.data.service.impl;

import com.TFG.tempo.data.dtos.ReunionAddDTO;
import com.TFG.tempo.data.entities.Reunion;
import com.TFG.tempo.data.entities.User;
import com.TFG.tempo.data.repository.ReunionRepository;
import com.TFG.tempo.data.repository.UserRepository;
import com.TFG.tempo.data.service.api.ReunionService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReunionServiceImpl implements ReunionService {

  @Autowired
  ReunionRepository reunionRepository;

  @Autowired
  UserRepository userRepository;

  @Override
  public Reunion findByCreatorUserId(Long userId) {
    return reunionRepository.findByCreatorUserId(userId);
  }

  @Override
  public List<Reunion> findByAssistantUserId(Long userId) {
    return reunionRepository.findByAssistantUserId(userId);
  }

  @Override
  public Reunion findByCreatorUserUsername(String username) {
    return reunionRepository.findByCreatorUsername(username);
  }

  @Override
  public List<Reunion> findByAssistantUsername(String username) {
    return reunionRepository.findByAssistantUsername(username);
  }

  @Override
  public List<User> findUsersByReunionId(Long reunionId) {
    return reunionRepository.findUsersByReunionId(reunionId);
  }

  @Override
  @Transactional
  public Reunion addAssistantByUserId(Long userId, Long notificationId) {
    Optional<Reunion> reunion = reunionRepository.findById(notificationId);
    if (reunion.isPresent()) {
      Optional<User> user = userRepository.findById(userId);
      user.ifPresent(value -> reunion.get().getAssistant().add(userId.intValue(), value));
      return reunion.get();
    }
    return null;
  }

  @Override
  public Reunion addAssistantByUsername(String username, Long notificationId) {
    Optional<Reunion> reunion = reunionRepository.findById(notificationId);
    if (reunion.isPresent()) {
      User user = userRepository.findByUsername(username);
      if (user != null) {
        reunion.get().getAssistant().add(user.getUserId().intValue(), user);
        return reunion.get();
      }
    }
    return null;
  }

  @Override
  public Reunion deleteAssistantByUserId(Long userId, Long notificationId) {
    Optional<Reunion> reunion = reunionRepository.findById(notificationId);
    if (reunion.isPresent()) {
      User user = reunion.get().getAssistant().get(userId.intValue());
      if (user != null) {
        reunion.get().getAssistant().remove(userId.intValue());
        return reunion.get();
      }
    }
    return null;
  }

  @Override
  public Reunion deleteAssistantByUsername(String username, Long notificationId) {
    User user = userRepository.findByUsername(username);
    Optional<Reunion> reunion = reunionRepository.findById(notificationId);
    if (reunion.isPresent()) {
      if (user != null) {
        if (reunion.get().getAssistant().get(user.getUserId().intValue()) != null) {
          reunion.get().getAssistant().remove(user.getUserId().intValue());
          return reunion.get();
        }
      }
    }
    return null;
  }

  @Override
  public Reunion addReunion(ReunionAddDTO reunionAddDTO) {
    Reunion reunion = new Reunion();
    User creator = userRepository.findByUsername(reunionAddDTO.getCreator());
    reunion.setAssistant(new ArrayList<>());
    reunion.setCreator(creator);
    reunion.setDate(reunionAddDTO.getDate());
    reunion.setDescription(reunionAddDTO.getDescription());
    reunionRepository.save(reunion);
    return reunion;
  }

  private Date transformDate(Date date) throws ParseException {
    String pattern = "yyyy-MM-dd'T'HH:mm:ss";
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    return sdf.parse(date.toString());
  }

}
