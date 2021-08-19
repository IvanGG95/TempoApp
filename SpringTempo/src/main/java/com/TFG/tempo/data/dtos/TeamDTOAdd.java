package com.TFG.tempo.data.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderClassName = "TeamDTOAddBuilder", toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = TeamDTOAdd.TeamDTOAddBuilder.class)
public class TeamDTOAdd {

  @NotEmpty
  private String name;

  @NotEmpty
  private String ownerUsername;

  @NotEmpty
  private String description;

  private List<String> users;

  @JsonPOJOBuilder(withPrefix = "")
  public static class TeamDTOAddBuilder {
  }
}
