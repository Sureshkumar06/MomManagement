package com.nova.mom.mappers;

import com.nova.mom.dtos.ReleaseDTO;
import com.nova.mom.entities.ReleaseMaster;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReleaseMasterMapper {
    ReleaseMasterMapper INSTANCE = Mappers.getMapper(ReleaseMasterMapper.class);

    ReleaseMaster toEntity(ReleaseDTO releaseDTO);
    ReleaseDTO toDto(ReleaseMaster releaseMaster);
}
