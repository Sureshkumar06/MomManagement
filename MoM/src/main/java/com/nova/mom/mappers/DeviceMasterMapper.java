package com.nova.mom.mappers;

import com.nova.mom.dtos.DeviceDTO;
import com.nova.mom.dtos.ReleaseDTO;
import com.nova.mom.entities.DeviceMaster;
import com.nova.mom.entities.ReleaseMaster;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeviceMasterMapper {
    DeviceMasterMapper INSTANCE = Mappers.getMapper(DeviceMasterMapper.class);

    DeviceMaster toEntity(DeviceDTO deviceDTO);
    DeviceDTO toDto(DeviceMaster deviceMaster);
}
