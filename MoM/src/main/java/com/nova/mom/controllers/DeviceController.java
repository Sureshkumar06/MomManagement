package com.nova.mom.controllers;

import com.nova.mom.dtos.DeviceDTO;
import com.nova.mom.dtos.DeviceResponseDTO;
import com.nova.mom.dtos.ReleaseDTO;
import com.nova.mom.services.DeviceService;
import com.nova.mom.utils.MoMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = MoMConstants.CONFIG_PATH_VERSION)
public class DeviceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    DeviceService deviceService;

    @PostMapping(MoMConstants.DEVICE_DETAILS)
    public ResponseEntity<List<DeviceResponseDTO>> deviceUpload(@RequestBody  List<DeviceDTO> deviceDTO) {
        LOGGER.info("device upload method start");
        List<DeviceResponseDTO> responseDto = deviceService.deviceUpload(deviceDTO);
        LOGGER.info("device upload method end");
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping(MoMConstants.DEVICE_DETAILS)
    public ResponseEntity<DeviceDTO> updateDevice(@RequestBody DeviceDTO deviceDTO) {
        LOGGER.info("update device method start");
        DeviceDTO responseDto = deviceService.updateDevice(deviceDTO);
        LOGGER.info("update device method end");
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(MoMConstants.DEVICE_DETAILS)
    public ResponseEntity<List<DeviceDTO>> getDeviceDetails() {
        LOGGER.info("get all device method start");
        List<DeviceDTO> responseDto = deviceService.getDeviceDetails();
        LOGGER.info("get all device method end");
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(MoMConstants.DEVICE_DETAILS + MoMConstants.DEVICE_MASTER_ID)
    public ResponseEntity<DeviceDTO> getDeviceDetailsById(@PathVariable Long deviceMasterId) {
        LOGGER.info("get by id device method start");
        DeviceDTO responseDto = deviceService.getDeviceDetailsById(deviceMasterId);
        LOGGER.info("get by id device method end");
        return ResponseEntity.ok().body(responseDto);
    }
}
