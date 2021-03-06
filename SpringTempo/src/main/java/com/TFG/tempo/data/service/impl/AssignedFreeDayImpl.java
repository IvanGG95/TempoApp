package com.TFG.tempo.data.service.impl;

import com.TFG.tempo.data.dtos.AssignedFreeDayDTO;
import com.TFG.tempo.data.dtos.DayDTO;
import com.TFG.tempo.data.entities.AssignedFreeDay;
import com.TFG.tempo.data.entities.User;
import com.TFG.tempo.data.mapper.AssignedFreeDaysMapper;
import com.TFG.tempo.data.repository.AssignedFreeDayRepository;
import com.TFG.tempo.data.repository.UserRepository;
import com.TFG.tempo.data.service.api.AssignedFreeDayService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignedFreeDayImpl implements AssignedFreeDayService {

  @Autowired
  public AssignedFreeDayRepository assignedFreeDayRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  AssignedFreeDaysMapper assignedFreeDaysMapper;

  @Override
  public void addAnAssignedDay(Date date, Long userId) {
    if (!assignedFreeDayRepository.existsByUserUserIdAndDate(userId, date)) {
      assignedFreeDayRepository.save(
          AssignedFreeDay.builder().date(date).user(userRepository.getOne(userId)).build());
    }
  }

  @Override
  public List<AssignedFreeDayDTO> addAnAssignedDayList(List<AssignedFreeDayDTO> assignedFreeDayDTOs) {
    List<AssignedFreeDayDTO> duplicatedDays = new ArrayList<>();
    for(AssignedFreeDayDTO dateDTO : assignedFreeDayDTOs){
      Optional<User> user = userRepository.findById(dateDTO.getUserId());
      if (!assignedFreeDayRepository.existsByUserUserIdAndDate(dateDTO.getUserId(), dateDTO.getDate()) && user.isPresent()) {
        assignedFreeDayRepository.save(AssignedFreeDay.builder().date(dateDTO.getDate()).user(user.get()).build());
      }else{
        duplicatedDays.add(dateDTO);
      }
    }
    return duplicatedDays;
  }


  @Override
  public List<AssignedFreeDayDTO> deleteAssignedDayList(List<AssignedFreeDayDTO> assignedFreeDayDTOs) {
    List<AssignedFreeDayDTO> notFoundDays = new ArrayList<>();
    for(AssignedFreeDayDTO dateDTO : assignedFreeDayDTOs){
      Optional<User> user = userRepository.findById(dateDTO.getUserId());
    if (assignedFreeDayRepository.existsByUserUserIdAndDate(dateDTO.getUserId(), dateDTO.getDate()) && user.isPresent()) {
    assignedFreeDayRepository.delete(assignedFreeDayRepository.findByUserUserIdAndDate(user.get().getUserId(), dateDTO.getDate()));
    } else{
      notFoundDays.add(dateDTO);
    }
    }
    return notFoundDays;
  }

  @Override
  public List<AssignedFreeDay> getAssignedDaysByUser(Long userId) {
    return assignedFreeDayRepository.findByUserUserId(userId);
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

  @Override
  public List<DayDTO> getMonthsCalendar(Date date, String userName){
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    List<DayDTO> days = new ArrayList<>();
    Date firstMondayWeek = getFirstMondayWeek(date);
    for(int i = 0; i< 48 ; i++){
      DayDTO day = new DayDTO();
      day.setAssignedFreeDays(getAssignedFreeDays(firstMondayWeek));
      day.setDayActual(getDayOfMonth(firstMondayWeek));
      day.setDate(format.format(firstMondayWeek));
      day.setWeekEnd(isWeekEnd(firstMondayWeek));
      if(isSameDay(firstMondayWeek,date)){
        day.setActual(true);
      }
      int maxHolidays = 3;
      for(AssignedFreeDayDTO assignedFreeDay : day.getAssignedFreeDays()){
        if(isSameDay(firstMondayWeek,assignedFreeDay.getDate()) && assignedFreeDay.getUsername().equals(userName)){
          day.setHolidays(true);
        }
        if(!day.isHolidays()){
          if(day.getAssignedFreeDays().size() >= maxHolidays){
            day.setTaken(true);
          }
        }
      }
      days.add(day);
      if(!((i % 8) == 0)){
        firstMondayWeek = addOneDay(firstMondayWeek);
      }

    }
    return days;
  }

  private boolean isWeekEnd(Date date){
    Calendar c1 = Calendar.getInstance();
    c1.setTime(date);

    return (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
        || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
  }

  private int getDayOfMonth(Date date){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.DAY_OF_MONTH);
  }

  private List<AssignedFreeDayDTO> getAssignedFreeDays(Date date){
    List<AssignedFreeDay> assignedFreeDays = assignedFreeDayRepository.findByDate(date);
    List<AssignedFreeDayDTO> assignedFreeDayDTOs = new ArrayList<>();
    for(AssignedFreeDay assignedFreeDay : assignedFreeDays){
      assignedFreeDayDTOs.add(assignedFreeDaysMapper.toAssignedFreeDayDTO(assignedFreeDay));
    }
    return  assignedFreeDayDTOs;
  }

  private Date addOneDay(Date date){
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DATE, 1);
    return c.getTime();
  }

  private Date getFirstMondayWeek(Date date){
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
    Date firstDayOfMonth = cal.getTime();
    cal.setTime(firstDayOfMonth);
    cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
    return cal.getTime();
  }

  private Boolean isSameDay(Date first , Date second){
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return format.format(first).equals(format.format(second));
  }
}
