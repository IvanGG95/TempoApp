package com.TFG.tempo.data.mapper;

import com.TFG.tempo.data.dtos.AssignedFreeDayDTO;
import com.TFG.tempo.data.entities.AssignedFreeDay;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class AssignedFreeDaysMapper {
  @Mappings({
      @Mapping(target = "userId", source = "user.userId")
  })
  public abstract AssignedFreeDayDTO toAssignedFreeDayDTO(AssignedFreeDay assignedFreeDay);

  public abstract AssignedFreeDay toAssignedFreeDay(AssignedFreeDayDTO assignedFreeDayDTO);
}
