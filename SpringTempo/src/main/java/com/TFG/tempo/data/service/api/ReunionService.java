package com.TFG.tempo.data.service.api;

import com.TFG.tempo.data.dtos.ReunionAddDTO;
import com.TFG.tempo.data.entities.Reunion;
import com.TFG.tempo.data.entities.User;
import java.util.List;

public interface ReunionService {

  List<Reunion> findByCreatorUserId(Long userId);

  List<Reunion> findByAssistantUserId(Long userId);

  List<Reunion> findByCreatorUserUsername(String username);

  List<Reunion> findByAssistantUsername(String username);

  List<User> findUsersByReunionId(Long reunionId);

  Reunion addAssistantByUserId(Long userId, Long notificationId);

  Reunion addAssistantByUsername(String username, Long notificationId);

  Reunion deleteAssistantByUserId(Long userId, Long notificationId);

  Reunion deleteAssistantByUsername(String username, Long notificationId);

  Reunion addReunion(ReunionAddDTO reunionAddDTO);

  Reunion addAssistantsToReunion(List<String> userNames, Long id);

  boolean deleteReunion(Long id);

  Reunion findById(Long id);
}
