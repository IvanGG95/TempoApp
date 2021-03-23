package com.TFG.tempo.data.dtos;


import com.TFG.tempo.data.entities.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderClassName = "UserDTOBuilder", toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(builder = UserDTO.UserDTOBuilder.class)
public class UserDTO implements Serializable {
  private Long userId;

  private int workedHours;

  private int availableFreeDays;

  private int weeklyWorkHours;

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  private String email;

  private User personInCharge;

  @JsonPOJOBuilder(withPrefix = "")
  public static class UserDTOBuilder {
  }
}
