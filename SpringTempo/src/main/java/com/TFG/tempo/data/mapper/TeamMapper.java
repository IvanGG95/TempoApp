package com.TFG.tempo.data.mapper;

import com.TFG.tempo.data.dtos.TeamDTO;
import com.TFG.tempo.data.entities.Team;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class TeamMapper {

  public abstract TeamDTO toTeamDTO(Team team);

}
