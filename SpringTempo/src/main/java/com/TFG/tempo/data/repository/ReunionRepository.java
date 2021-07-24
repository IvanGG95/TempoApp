package com.TFG.tempo.data.repository;


import com.TFG.tempo.data.entities.Reunion;
import com.TFG.tempo.data.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReunionRepository extends JpaRepository<Reunion, Long> {

  Reunion findByCreatorUserId(Long userId);

  List<Reunion> findByAssistantUserId(Long userId);

  Reunion findByCreatorUsername(String username);

  List<Reunion> findByAssistantUsername(String username);

  List<User> findUsersByReunionId(Long reunionId);
}
