package com.TFG.tempo.data.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotEmpty;
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

  @NotEmpty
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private Date date;

  @NotEmpty
  private String creator;

  @NotEmpty
  private String description;

  private List<String> users;

  @NotEmpty
  private Long teamId;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ReunionAddDTOBuilder {
  }
}
