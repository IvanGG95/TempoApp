package com.TFG.tempo.data.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderClassName = "DayDTOBuilder", toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = DayDTO.DayDTOBuilder.class)
public class DayDTO {
  boolean actual = false;

  boolean free = false;

  boolean festive = false;

  boolean taken = false;

  boolean holidays = false;

  boolean weekEnd = false;

  List<AssignedFreeDayDTO> assignedFreeDays;

  List<ReunionDTO> reunions;

  String date;

  int dayActual;

  @JsonPOJOBuilder(withPrefix = "")
  public static class DayDTOBuilder {
  }
}
