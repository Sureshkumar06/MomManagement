package com.nova.mom.controllers;

import com.nova.mom.dtos.CustomerDTO;
import com.nova.mom.dtos.DeviceGroupDTO;
import com.nova.mom.services.CustomerService;
import com.nova.mom.services.DeviceGroupService;
import com.nova.mom.utils.MoMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = MoMConstants.CONFIG_PATH_VERSION)
public class DeviceGroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceGroupController.class);

    @Autowired
    DeviceGroupService deviceGroupService;

    @PostMapping(MoMConstants.DEVICE_GROUP_DETAILS)
    public ResponseEntity<DeviceGroupDTO> createDeviceGroup(@RequestBody DeviceGroupDTO deviceGroupDTO) {
        DeviceGroupDTO responseDto = deviceGroupService.createDeviceGroup(deviceGroupDTO);
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping(MoMConstants.DEVICE_GROUP_DETAILS)
    public ResponseEntity<DeviceGroupDTO> updateDeviceGroup(@RequestBody DeviceGroupDTO deviceGroupDTO) {
        DeviceGroupDTO responseDto = deviceGroupService.updateDeviceGroup(deviceGroupDTO);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(MoMConstants.DEVICE_GROUP_DETAILS)
    public ResponseEntity<List<DeviceGroupDTO>> getDeviceGroupDetails() {
        List<DeviceGroupDTO> responseDto = deviceGroupService.getDeviceGroupDetails();
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(MoMConstants.DEVICE_GROUP_DETAILS + MoMConstants.DEVICE_GROUP_ID)
    public ResponseEntity<DeviceGroupDTO> getDeviceGroupDetailsById(@PathVariable Long deviceGroupId) {
        DeviceGroupDTO responseDto = deviceGroupService.getDeviceGroupDetailsById(deviceGroupId);
        return ResponseEntity.ok().body(responseDto);
    }

}
