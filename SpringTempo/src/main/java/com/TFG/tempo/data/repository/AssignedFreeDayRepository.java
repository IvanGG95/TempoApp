package com.TFG.tempo.data.repository;

import com.TFG.tempo.data.entities.AssignedFreeDay;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignedFreeDayRepository extends JpaRepository<AssignedFreeDay, Long> {
  List<AssignedFreeDay> findByUserUserId(Long userId);

  boolean existsByUserUserIdAndDate(Long userId, Date date);

  AssignedFreeDay findByUserUserIdAndDate(Long userId, Date date);

  @Query(value = "SELECT * FROM assigned_free_day where assigned_free_day.date BETWEEN ?1 AND ?2",
      nativeQuery = true)
  List<AssignedFreeDay> findAllBetweenTwoMonths(Date startDate, Date endDate);

  List<AssignedFreeDay> findByDate(Date date);
}
