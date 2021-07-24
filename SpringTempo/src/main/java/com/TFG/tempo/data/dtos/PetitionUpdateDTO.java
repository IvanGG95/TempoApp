package com.TFG.tempo.data.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderClassName = "PetitionUpdateDTOBuilder", toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = PetitionUpdateDTO.PetitionUpdateDTOBuilder.class)
public class PetitionUpdateDTO {

  @NotEmpty
  private Long petitionId;

  @NotEmpty
  private String status;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PetitionUpdateDTOBuilder {
  }

}
