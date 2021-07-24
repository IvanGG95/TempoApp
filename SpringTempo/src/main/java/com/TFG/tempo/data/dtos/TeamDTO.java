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
@Builder(builderClassName = "TeamDTOBuilder", toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = TeamDTO.TeamDTOBuilder.class)
public class TeamDTO {

  private Date creationDate;

  private String name;

  private UserDTO owner;

  private List<UserDTO> employees;

  private String description;

  @JsonPOJOBuilder(withPrefix = "")
  public static class TeamDTOBuilder {
  }
}
