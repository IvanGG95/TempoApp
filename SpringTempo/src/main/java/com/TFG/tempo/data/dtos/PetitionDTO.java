package com.TFG.tempo.data.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderClassName = "PetitionDTOBuilder", toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = PetitionDTO.PetitionDTOBuilder.class)
public class PetitionDTO {

  private Long petitionId;

  private Date creationDate;

  private UserDTO creator;

  private UserDTO receiver;

  private ReunionDTO reunion;

  private TeamDTO team;

  private AssignedFreeDayDTO assignedFreeDay;

  private String status;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PetitionDTOBuilder {
  }
}
