package com.TFG.tempo.data.service.impl;

import com.TFG.tempo.data.entities.AssignedFreeDay;
import com.TFG.tempo.data.repository.AssignedFreeDayRepository;
import com.TFG.tempo.data.repository.UserRepository;
import com.TFG.tempo.data.service.api.AssignedFreeDayService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignedFreeDayImpl implements AssignedFreeDayService {

  @Autowired
  public AssignedFreeDayRepository assignedFreeDayRepository;

  @Autowired
  UserRepository userRepository;

  @Override
  public void addAnAssignedDay(Date date, Long userId) {
    if (!assignedFreeDayRepository.existsByUserUserIdAndDate(userId, date)) {
      assignedFreeDayRepository.save(
          AssignedFreeDay.builder().date(date).user(userRepository.getOne(userId)).build());
    }
  }

  @Override
  public void addAssignedDays(Date first, Date second, Long userId) {
    Calendar calFirst = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
    Calendar calSecond = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
    calFirst.setTime(first);
    calSecond.setTime(second);
    for (int i = calFirst.get(Calendar.DAY_OF_MONTH); i < calSecond.get(Calendar.DAY_OF_MONTH); i++) {
      if (!assignedFreeDayRepository.existsByUserUserIdAndDate(userId, calFirst.getTime())) {
        assignedFreeDayRepository.save(
            AssignedFreeDay.builder().date(calFirst.getTime()).user(userRepository.getOne(userId)).build());
      }
      calFirst.add(Calendar.DAY_OF_MONTH, 1);
    }
  }

  @Override
  @Transactional
  public void deleteAssignedDay(Date date, Long userId) {
    assignedFreeDayRepository.deleteAssignedFreeDayByUserUserIdAndDate(userId, date);
  }

  @Override
  @Transactional
  public void deleteAssignedDays(Date first, Date second, Long userId) {
    Calendar calFirst = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
    Calendar calSecond = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
    calFirst.setTime(first);
    calSecond.setTime(second);
    for (int i = calFirst.get(Calendar.DAY_OF_MONTH); i < calSecond.get(Calendar.DAY_OF_MONTH); i++) {
      assignedFreeDayRepository.deleteAssignedFreeDayByUserUserIdAndDate(userId, calFirst.getTime());
      calFirst.add(Calendar.DAY_OF_MONTH, 1);
    }
  }

  @Override
  public List<AssignedFreeDay> getAssignedDaysByUser(Long userId) {
    return assignedFreeDayRepository.findByUserUserId(userId);
  }

  @Override
  public AssignedFreeDay getAnAssignedDay(Long id) {
    return assignedFreeDayRepository.getOne(id);
  }

  @Override
  public List<AssignedFreeDay> getAssignedDaysByMonth(Date month) {
    Calendar calFirst = Calendar.getInstance(TimeZone.getTimeZone("Europe/Madrid"));
    calFirst.setTime(month);

    calFirst.set(Calendar.DAY_OF_MONTH, 1);
    Date first = calFirst.getTime();

    calFirst.set(Calendar.DAY_OF_MONTH, calFirst.getActualMaximum(Calendar.DAY_OF_MONTH));
    Date second = calFirst.getTime();

    return assignedFreeDayRepository.findAllBetweenTwoMonths(first, second);
  }
}
