package com.TFG.tempo.data.dtos;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

  private static final long serialVersionUID = -670135840145753852L;

  @NotBlank(message = "Es necesario introducir un nombre")
  private String username;

  @NotBlank(message = "Es necesario introducir una contraseña")
  @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
  private String password;

  @NotBlank(message = "Es necesario introducir un email")
  @Email(message = "Email incorrecto")
  private String email;

  private Long userId;

  private int workedHours;

  private int availableFreeDays;

  private int weeklyWorkHours;

  private Long personInChargeId;

  private List<RoleDTO> roles;

  @JsonPOJOBuilder(withPrefix = "")
  public static class UserDTOBuilder {
  }
}
