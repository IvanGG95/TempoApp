package com.TFG.tempo.data.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder(builderClassName = "AssignedFreeDayDTOBuilder", toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = AssignedFreeDayDTO.AssignedFreeDayDTOBuilder.class)
public class AssignedFreeDayDTO implements Serializable {

  private Long freeDayId;

  @NotNull
  private Date date;

  @NotNull
  private Long userId;

  private String username;

  private String status;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AssignedFreeDayDTOBuilder {
  }
}
