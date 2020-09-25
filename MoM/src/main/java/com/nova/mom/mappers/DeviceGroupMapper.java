package com.nova.mom.mappers;

import com.nova.mom.dtos.CustomerDTO;
import com.nova.mom.dtos.DeviceGroupDTO;
import com.nova.mom.entities.CustomerMaster;
import com.nova.mom.entities.DeviceGroup;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeviceGroupMapper {
    DeviceGroupMapper INSTANCE = Mappers.getMapper(DeviceGroupMapper.class);

    DeviceGroup toEntity(DeviceGroupDTO deviceGroupDTO);
    DeviceGroupDTO toDto(DeviceGroup deviceGroup);
}
