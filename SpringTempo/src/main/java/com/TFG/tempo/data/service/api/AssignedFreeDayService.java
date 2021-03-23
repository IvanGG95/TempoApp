package com.TFG.tempo.data.service.api;

import com.TFG.tempo.data.entities.AssignedFreeDay;
import java.util.Date;
import java.util.List;

public interface AssignedFreeDayService {
  void addAnAssignedDay(Date date, Long userId);

  void addAssignedDays(Date fist, Date second, Long userId);

  void deleteAssignedDay(Date date, Long userId);

  void deleteAssignedDays(Date fist, Date second, Long userId);

  List<AssignedFreeDay> getAssignedDaysByUser(Long userId);

  AssignedFreeDay getAnAssignedDay(Long id);

  List<AssignedFreeDay> getAssignedDaysByMonth(Date month);

}
