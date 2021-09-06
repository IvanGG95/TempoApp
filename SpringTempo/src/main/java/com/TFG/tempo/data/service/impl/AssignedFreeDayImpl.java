package com.TFG.tempo.data.service.impl;

import com.TFG.tempo.data.dtos.AssignedFreeDayDTO;
import com.TFG.tempo.data.dtos.DayDTO;
import com.TFG.tempo.data.dtos.ReunionDTO;
import com.TFG.tempo.data.entities.AssignedFreeDay;
import com.TFG.tempo.data.entities.Reunion;
import com.TFG.tempo.data.entities.Team;
import com.TFG.tempo.data.entities.User;
import com.TFG.tempo.data.mapper.AssignedFreeDaysMapper;
import com.TFG.tempo.data.mapper.ReunionMapper;
import com.TFG.tempo.data.repository.AssignedFreeDayRepository;
import com.TFG.tempo.data.repository.ReunionRepository;
import com.TFG.tempo.data.repository.TeamRepository;
import com.TFG.tempo.data.repository.UserRepository;
import com.TFG.tempo.data.service.api.AssignedFreeDayService;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignedFreeDayImpl implements AssignedFreeDayService {

  @Autowired
  ReunionMapper reunionMapper;
  @Autowired
  UserRepository userRepository;
  @Autowired
  AssignedFreeDaysMapper assignedFreeDaysMapper;
  @Autowired
  private AssignedFreeDayRepository assignedFreeDayRepository;
  @Autowired
  private ReunionRepository reunionRepository;

  @Autowired
  TeamRepository teamRepository;

  @Override
  public void addAnAssignedDay(Date date, Long userId) {
    if (!assignedFreeDayRepository.existsByUserUserIdAndDate(userId, date)) {
      User user = userRepository.getOne(userId);
      assignedFreeDayRepository.save(
          AssignedFreeDay.builder().date(date).user(user).build());
      updateFreeDaysUser(userId);
    }
  }

  @Override
  public List<AssignedFreeDayDTO> addAnAssignedDayList(List<AssignedFreeDayDTO> assignedFreeDayDTOs) {
    List<AssignedFreeDayDTO> duplicatedDays = new ArrayList<>();
    for (AssignedFreeDayDTO dateDTO : assignedFreeDayDTOs) {
      Optional<User> user = userRepository.findById(dateDTO.getUserId());
      if (!assignedFreeDayRepository.existsByUserUserIdAndDate(dateDTO.getUserId(), dateDTO.getDate()) &&
          user.isPresent()) {
        assignedFreeDayRepository.save(AssignedFreeDay.builder().date(dateDTO.getDate()).user(user.get()).build());
        updateFreeDaysUser(dateDTO.getUserId());
      } else {
        duplicatedDays.add(dateDTO);
      }
    }
    return duplicatedDays;
  }


  @Override
  public List<AssignedFreeDayDTO> deleteAssignedDayList(List<AssignedFreeDayDTO> assignedFreeDayDTOs) {
    List<AssignedFreeDayDTO> notFoundDays = new ArrayList<>();
    for (AssignedFreeDayDTO dateDTO : assignedFreeDayDTOs) {
      Optional<User> user = userRepository.findById(dateDTO.getUserId());
      if (assignedFreeDayRepository.existsByUserUserIdAndDate(dateDTO.getUserId(), dateDTO.getDate()) &&
          user.isPresent()) {
        assignedFreeDayRepository
            .delete(assignedFreeDayRepository.findByUserUserIdAndDate(user.get().getUserId(), dateDTO.getDate()));
        updateFreeDaysUserDelete(user.get().getUserId());
      } else {
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
  public List<DayDTO> getMonthsCalendar(Date date, String userName) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    List<DayDTO> days = new ArrayList<>();
    Date firstMondayWeek = getFirstMondayWeek(date);
    for (int i = 0; i < 48; i++) {
      DayDTO day = new DayDTO();
      day.setAssignedFreeDays(relatedPersons(getAssignedFreeDays(firstMondayWeek), userName));
      day.setDayActual(getDayOfMonth(firstMondayWeek));
      day.setDate(format.format(firstMondayWeek));
      day.setWeekEnd(isWeekEnd(firstMondayWeek));
      day.setReunions(getReunions(firstMondayWeek, userName));
      if (isSameDay(firstMondayWeek, date)) {
        day.setActual(true);
      }
      int maxHolidays = 3;
      for (AssignedFreeDayDTO assignedFreeDay : day.getAssignedFreeDays()) {
        if (isSameDay(firstMondayWeek, assignedFreeDay.getDate()) && assignedFreeDay.getUsername().equals(userName)) {
          day.setHolidays(true);
        }
        if (!day.isHolidays()) {
          if (day.getAssignedFreeDays().size() >= maxHolidays) {
            day.setTaken(true);
          }
        }
      }
      days.add(day);
      if (!((i % 8) == 0)) {
        firstMondayWeek = addOneDay(firstMondayWeek);
      }

    }
    return days;
  }

  private boolean isWeekEnd(Date date) {
    Calendar c1 = Calendar.getInstance();
    c1.setTime(date);

    return (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
        || (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
  }

  private int getDayOfMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.DAY_OF_MONTH);
  }

  private List<AssignedFreeDayDTO> getAssignedFreeDays(Date date) {
    List<AssignedFreeDay> assignedFreeDays = assignedFreeDayRepository.findByDate(date);
    List<AssignedFreeDayDTO> assignedFreeDayDTOs = new ArrayList<>();
    for (AssignedFreeDay assignedFreeDay : assignedFreeDays) {
      assignedFreeDayDTOs.add(assignedFreeDaysMapper.toAssignedFreeDayDTO(assignedFreeDay));
    }
    return assignedFreeDayDTOs;
  }

  private List<ReunionDTO> getReunions(Date date, String username) {
    LocalDate actualDate = convertToLocalDateTimeViaInstant(date);
    LocalDateTime startOfDay = LocalDateTime.of(actualDate, LocalTime.MIDNIGHT);
    LocalDateTime endOfDate = LocalDateTime.of(actualDate, LocalTime.MAX);
    SimpleDateFormat formatterDay = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatterHour = new SimpleDateFormat("HH:mm:ss");
    List<Reunion> reunions =
        reunionRepository.findByDate(convertToDateViaInstant(startOfDay), convertToDateViaInstant(endOfDate));
    List<ReunionDTO> reunionsDTO = new ArrayList<>();
    for (Reunion reunion : reunions) {
      if (reunion.getCreator().getUsername().equals(username)) {
        ReunionDTO reunionDTO = reunionMapper.toReunionDTO(reunion);
        reunionDTO.setDays(formatterDay.format(reunionDTO.getDate()));
        reunionDTO.setHours(formatterHour.format(reunionDTO.getDate()));
        reunionsDTO.add(reunionDTO);
      }
      for (User user : reunion.getAssistant()) {
        if (user.getUsername().equals(username)) {
          ReunionDTO reunionDTO = reunionMapper.toReunionDTO(reunion);
          reunionDTO.setDays(formatterDay.format(reunionDTO.getDate()));
          reunionDTO.setHours(formatterHour.format(reunionDTO.getDate()));
          reunionsDTO.add(reunionDTO);
        }
      }
    }
    return reunionsDTO;
  }

  public LocalDate convertToLocalDateTimeViaInstant(Date dateToConvert) {
    return dateToConvert.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
  }

  public Date convertToDateViaInstant(LocalDateTime dateToConvert) {
    return java.util.Date
        .from(dateToConvert.atZone(ZoneId.systemDefault())
            .toInstant());
  }

  private Date addOneDay(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(Calendar.DATE, 1);
    return c.getTime();
  }

  private Date getFirstMondayWeek(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
    Date firstDayOfMonth = cal.getTime();
    cal.setTime(firstDayOfMonth);
    cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
    return cal.getTime();
  }

  private Boolean isSameDay(Date first, Date second) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return format.format(first).equals(format.format(second));
  }

  private void updateFreeDaysUser(Long userId) {
    User user = this.userRepository.findById(userId).isPresent() ? this.userRepository.findById(userId).get() : null;
    assert user != null;
    user.setAvailableFreeDays(user.getAvailableFreeDays() - 1);
    userRepository.save(user);
  }

  private void updateFreeDaysUserDelete(Long userId) {
    User user = this.userRepository.findById(userId).isPresent() ? this.userRepository.findById(userId).get() : null;
    assert user != null;
    user.setAvailableFreeDays(user.getAvailableFreeDays() + 1);
    userRepository.save(user);
  }


  List<User> findRelatedUsersByUsername(String username) {

    List<Team> teamsEmployee = teamRepository.findByEmployeesUsername(username);
    List<Team> teamsOwner = teamRepository.findByOwnerUsername(username);
    HashSet usernames = new HashSet();
    List<User> users = new ArrayList<>();
    users.add(userRepository.findByUsername(username));
    for (Team team : teamsEmployee) {
      for (User user : team.getEmployees()) {
        usernames.add(user.getUsername());
      }
      usernames.add(team.getOwner().getUsername());
    }

    for (Team team : teamsOwner) {
      for (User user : team.getEmployees()) {
        usernames.add(user.getUsername());
      }
    }

    for (Object name : usernames) {
      users.add(userRepository.findByUsername(name));
    }

    return users;
  }

  List<AssignedFreeDayDTO> relatedPersons(List<AssignedFreeDayDTO> assignedFreeDays, String username) {
    List<User> users = findRelatedUsersByUsername(username);
    List<AssignedFreeDayDTO> related = new ArrayList<>();
    for (AssignedFreeDayDTO assignedFreeDayDTO : assignedFreeDays) {
      for (User user : users) {
        if (user.getUsername().equals(assignedFreeDayDTO.getUsername())) {
          related.add(assignedFreeDayDTO);
        }
      }
    }
    return related;
  }
}
