package com.TFG.tempo.data.repository;


import com.TFG.tempo.data.entities.Reunion;
import com.TFG.tempo.data.entities.User;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReunionRepository extends JpaRepository<Reunion, Long> {

  List<Reunion> findByCreatorUserId(Long userId);

  List<Reunion> findByAssistantUserId(Long userId);

  List<Reunion> findByCreatorUsername(String username);

  List<Reunion> findByAssistantUsername(String username);

  List<User> findUsersByReunionId(Long reunionId);

  @Query(value = "SELECT * FROM reunion where reunion.date BETWEEN ?1 AND ?2",
      nativeQuery = true)
  List<Reunion> findByDate(Date date, Date endDate);
}
