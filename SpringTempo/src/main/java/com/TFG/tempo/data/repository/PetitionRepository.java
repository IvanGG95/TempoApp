package com.TFG.tempo.data.repository;

import com.TFG.tempo.data.entities.Petition;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetitionRepository extends JpaRepository<Petition, Long> {

  List<Petition> findByCreatorUserId(Long userId);

  List<Petition> findByReceiverUserId(Long userId);

  List<Petition> findByReceiverUserIdAndStatus(Long userId, String status);

  List<Petition> findByCreatorUserIdAndStatus(Long userId, String status);

  List<Petition> findByTeamTeamId(Long teamId);

  List<Petition> findByReunionReunionId(Long reunionId);
}
