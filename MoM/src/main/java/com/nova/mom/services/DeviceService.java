package com.nova.mom.services;

import com.nova.mom.dtos.DeviceDTO;
import com.nova.mom.dtos.DeviceResponseDTO;
import com.nova.mom.dtos.ReleaseDTO;

import java.util.List;

public interface DeviceService {
    List<DeviceResponseDTO> deviceUpload(List<DeviceDTO> deviceDTO);

    DeviceDTO updateDevice(DeviceDTO deviceDTO);

    List<DeviceDTO> getDeviceDetails();

    DeviceDTO getDeviceDetailsById(Long deviceMasterId);
}
