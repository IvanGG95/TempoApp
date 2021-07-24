package com.TFG.tempo.data.mapper;

import com.TFG.tempo.data.dtos.PetitionDTO;
import com.TFG.tempo.data.entities.Petition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PetitionMapper {

  public abstract PetitionDTO toPetitionDTO(Petition petition);

}
