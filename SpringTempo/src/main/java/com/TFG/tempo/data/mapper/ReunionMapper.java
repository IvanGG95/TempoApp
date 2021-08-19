package com.TFG.tempo.data.mapper;

import com.TFG.tempo.data.dtos.ReunionDTO;
import com.TFG.tempo.data.entities.Reunion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ReunionMapper {

  public abstract ReunionDTO toReunionDTO(Reunion reunion);

}
