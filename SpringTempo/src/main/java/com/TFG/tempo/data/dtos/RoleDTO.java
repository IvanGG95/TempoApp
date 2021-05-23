package com.TFG.tempo.data.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderClassName = "RoleDTOBuilder", toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = RoleDTO.RoleDTOBuilder.class)
public class RoleDTO {
  @NotNull
  private String name;

  @JsonPOJOBuilder(withPrefix = "")
  public static class RoleDTOBuilder {
  }
}
