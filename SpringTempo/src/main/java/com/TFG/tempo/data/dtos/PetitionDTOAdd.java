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
@Builder(builderClassName = "PetitionDTOAddBuilder", toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = PetitionDTOAdd.PetitionDTOAddBuilder.class)
public class PetitionDTOAdd {

  private Long petitionId;

  @NotEmpty
  private String creator;

  @NotEmpty
  private String receiver;

  private Long reunion;

  private Long team;

  private String status;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PetitionDTOAddBuilder {
  }
}
