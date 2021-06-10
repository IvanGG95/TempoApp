package com.TFG.tempo.data.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderClassName = "AuthenticationDTOBuilder", toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = AuthenticationDTO.AuthenticationDTOBuilder.class)
public class AuthenticationDTO {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AuthenticationDTOBuilder {
  }
}
