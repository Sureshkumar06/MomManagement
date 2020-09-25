package com.nova.mom.services;

import com.nova.mom.dtos.DeviceGroupDTO;

import java.util.List;

public interface DeviceGroupService {
    DeviceGroupDTO createDeviceGroup(DeviceGroupDTO deviceGroupDTO);

    DeviceGroupDTO updateDeviceGroup(DeviceGroupDTO deviceGroupDTO);

    List<DeviceGroupDTO> getDeviceGroupDetails();

    DeviceGroupDTO getDeviceGroupDetailsById(Long deviceGroupId);
}
