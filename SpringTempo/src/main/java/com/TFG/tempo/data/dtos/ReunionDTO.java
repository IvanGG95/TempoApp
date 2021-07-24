package com.TFG.tempo.data.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderClassName = "ReunionDTOBuilder", toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = ReunionDTO.ReunionDTOBuilder.class)
public class ReunionDTO {

  private Date date;

  private UserDTO creator;

  private List<UserDTO> assistant;

  private String description;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ReunionDTOBuilder {
  }
}
