package com.TFG.tempo.data.service.api;

import com.TFG.tempo.data.dtos.AssignedFreeDayDTO;
import com.TFG.tempo.data.dtos.DayDTO;
import com.TFG.tempo.data.entities.AssignedFreeDay;
import java.util.Date;
import java.util.List;

public interface AssignedFreeDayService {
  void addAnAssignedDay(Date date, Long userId);

  List<AssignedFreeDayDTO> addAnAssignedDayList(List<AssignedFreeDayDTO> assignedFreeDayDTOs);

  List<AssignedFreeDayDTO> deleteAssignedDayList(List<AssignedFreeDayDTO> assignedFreeDayDTOs);

  List<AssignedFreeDay> getAssignedDaysByUser(Long userId);

  List<AssignedFreeDay> getAssignedDaysByMonth(Date month);

  List<DayDTO> getMonthsCalendar(Date date, String userName);

}
