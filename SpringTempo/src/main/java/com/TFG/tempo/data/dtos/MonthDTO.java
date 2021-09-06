package com.TFG.tempo.data.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderClassName = "MonthDTOBuilder", toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = MonthDTO.MonthDTOBuilder.class)
public class MonthDTO {

  @NotNull(message = "Es necesario introducir una fecha")
  Date date;

  @NotBlank(message = "Es necesario introducir un nombre")
  String userName;

  @JsonPOJOBuilder(withPrefix = "")
  public static class MonthDTOBuilder {
  }
}
