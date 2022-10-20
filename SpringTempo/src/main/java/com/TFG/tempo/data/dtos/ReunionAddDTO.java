package com.TFG.tempo.data.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderClassName = "ReunionAddDTOBuilder", toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = ReunionAddDTO.ReunionAddDTOBuilder.class)
public class ReunionAddDTO {

  @NotNull(message = "Es necesario introducir una fecha")
  private Date date;

  @NotBlank(message = "Es necesario introducir un creador")
  private String creator;

  @NotBlank(message = "Es necesario introducir una descripcion")
  @Size(min = 1, max = 400, message = "Texto demasiado grande, como maximo 800 caracteres")
  private String description;

  private List<String> users;

  @NotNull(message = "Es necesario asignar un equipo")
  private Long teamId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ReunionAddDTOBuilder {
  }
}
