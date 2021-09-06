package com.TFG.tempo.data.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private Date date;

  @NotBlank(message = "Es necesario introducir un creador")
  private String creator;

  @NotBlank(message = "Es necesario introducir una descripcion")
  private String description;

  private List<String> users;

  @NotNull(message = "Es necesario asgnar un equipo")
  private Long teamId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ReunionAddDTOBuilder {
  }
}
