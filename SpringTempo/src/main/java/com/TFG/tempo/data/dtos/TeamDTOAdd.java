package com.TFG.tempo.data.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

  @NotBlank(message = "Es necesario introducir un nombre")
  private String name;

  @NotBlank(message = "Es necesario introducir un propietario")
  private String ownerUsername;

  @NotBlank(message = "Es necesario introducir una descripcion")
  @Size(max = 255, message = "Descripcion demasiado larga")
  private String description;

  private List<String> users;

  @JsonPOJOBuilder(withPrefix = "")
  public static class TeamDTOAddBuilder {
  }
}
