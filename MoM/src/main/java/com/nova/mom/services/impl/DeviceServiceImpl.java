package com.nova.mom.services.impl;

import com.nova.mom.dtos.CustomerDTO;
import com.nova.mom.dtos.DeviceDTO;
import com.nova.mom.dtos.DeviceResponseDTO;
import com.nova.mom.dtos.ReleaseDTO;
import com.nova.mom.entities.CustomerMaster;
import com.nova.mom.entities.DeviceGroup;
import com.nova.mom.entities.DeviceMaster;
import com.nova.mom.entities.ReleaseMaster;
import com.nova.mom.mappers.CustomerMasterMapper;
import com.nova.mom.mappers.DeviceMasterMapper;
import com.nova.mom.mappers.ReleaseMasterMapper;
import com.nova.mom.repositories.DeviceGroupRepository;
import com.nova.mom.repositories.DeviceMasterRepository;
import com.nova.mom.services.DeviceService;
import liquibase.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    private DeviceMasterRepository deviceMasterRepository;

    @Autowired
    private DeviceGroupRepository deviceGroupRepository;

    @Override
    public List<DeviceResponseDTO> deviceUpload(List<DeviceDTO> deviceDTO) {
        LOGGER.info("upload device service method start");
        List<DeviceResponseDTO> responce = new ArrayList<>();
        if(deviceDTO !=null){
            deviceDTO.stream().map(device -> {
                DeviceResponseDTO deviceResponce = new DeviceResponseDTO();
                DeviceMaster deviceMaster = new DeviceMaster();
                if(device.getDeviceGroupId() !=null){
                    DeviceGroup deviceGroup = new DeviceGroup();
                    deviceGroup.setDeviceGroupId(device.getDeviceGroupId());
                    deviceMaster.setDeviceGroup(deviceGroup);
                }else if(device.getCustomerId() !=null && device.getDeviceGroupId() == null){
                    Optional<DeviceGroup> deviceGroup = deviceGroupRepository.findByCustomerMaster_CustomerIdAndDeviceGroupName(device.getCustomerId() ,"Default");
                    deviceMaster.setDeviceGroup(deviceGroup.get());
                } else {
                    LOGGER.info("give valid input in device service upload");
                    deviceResponce.setDeviceId(device.getDeviceId());
                    deviceResponce.setStatus(false);
                    deviceResponce.setMessage("Give Valid Input");
                }
                deviceMaster.setDeviceId(device.getDeviceId());
                deviceMaster.setDeviceName(device.getDeviceName());

                deviceMaster.setActive("Y");
                deviceMaster.setCreatedAt(new Date());
                deviceMaster.setCreatedBy("Admin");
                deviceMaster.setUpdatedAt(new Date());
                deviceMaster.setUpdatedBy("Admin");
                deviceMaster.setVersion("V1.0");
                deviceMaster.setRecVerNo(1);
                DeviceMaster deviceMaster1 = deviceMasterRepository.save(deviceMaster);
                deviceResponce.setDeviceId(deviceMaster1.getDeviceId());
                deviceResponce.setDeviceMasterId(deviceMaster1.getDeviceMasterId());
                deviceResponce.setStatus(true);
                responce.add(deviceResponce);
               return null;
            }).collect(Collectors.toList());
        }
        LOGGER.info("upload device service method end");
        return responce;
    }

    @Override
    public DeviceDTO updateDevice(DeviceDTO deviceDTO) {
        LOGGER.info("update device service method start");
        DeviceDTO deviceDTORes = new DeviceDTO();
        if(deviceDTO !=null){
            if(deviceDTO.getDeviceMasterId() !=null){
                Optional<DeviceMaster> deviceMasterOptional = deviceMasterRepository.findById(deviceDTO.getDeviceMasterId());
                DeviceMaster deviceMaster = null;
                if (deviceMasterOptional.isPresent()) {
                    deviceMaster = deviceMasterOptional.get();
                } else {
                    LOGGER.info("device data not available in update");
                    deviceDTORes.setDeviceMasterId(deviceDTO.getDeviceMasterId());
                    deviceDTORes.setErrorStatus(true);
                    deviceDTORes.setErrorMessage("Data Not Available");
                    return deviceDTORes;
                }
                deviceMaster = DeviceMasterMapper.INSTANCE.toEntity(deviceDTO);
                if(deviceDTO.getDeviceGroupId() !=null){
                    deviceMaster.setDeviceGroup(deviceGroupRepository.findById(deviceDTO.getDeviceGroupId()).get());
                }else if(deviceDTO.getCustomerId() !=null && deviceDTO.getDeviceGroupId() ==null){
                    deviceMaster.setDeviceGroup(deviceGroupRepository.findByCustomerMaster_CustomerIdAndDeviceGroupName(deviceDTO.getCustomerId() ,"Default").get());
                }
                if (deviceMasterRepository.save(deviceMaster) != null) {
                    deviceDTORes = getDeviceDetailsById(deviceDTO.getDeviceMasterId());
                }
            }
        }
        LOGGER.info("update device service method end");
        return deviceDTORes;
    }

    @Override
    public List<DeviceDTO> getDeviceDetails() {
        List<DeviceDTO> deviceDTOList = new ArrayList<>();
        List<DeviceMaster> deviceMasterList = deviceMasterRepository.findAll();
        if(deviceMasterList !=null && !deviceMasterList.isEmpty()){
            deviceMasterList.stream().map(device ->{
                DeviceDTO deviceDTO = new DeviceDTO();
                deviceDTO = DeviceMasterMapper.INSTANCE.toDto(device);
                deviceDTO.setCustomerId(device.getDeviceGroup().getCustomerMaster().getCustomerId());
                deviceDTO.setDeviceGroupId(device.getDeviceGroup().getDeviceGroupId());
                deviceDTOList.add(deviceDTO);
                return null;
            }).collect(Collectors.toList());
        }
        return deviceDTOList;
    }

    @Override
    public DeviceDTO getDeviceDetailsById(Long deviceMasterId) {
        LOGGER.info("get device details by id service method start");
        DeviceDTO deviceDTO = new DeviceDTO();
        if(deviceMasterId !=null){
            Optional<DeviceMaster> deviceMaster = deviceMasterRepository.findByDeviceMasterId(deviceMasterId);
            if(deviceMaster.isPresent()){
                deviceDTO = DeviceMasterMapper.INSTANCE.toDto(deviceMaster.get());
                deviceDTO.setCustomerId(deviceMaster.get().getDeviceGroup().getCustomerMaster().getCustomerId());
                deviceDTO.setDeviceGroupId(deviceMaster.get().getDeviceGroup().getDeviceGroupId());
            }
        }else{
            LOGGER.info("invalid id for getDeviceDetailsById");
            deviceDTO.setErrorMessage("Give Valid Id");
            deviceDTO.setErrorStatus(true);
        }
        LOGGER.info("get device details by id service method end");
        return deviceDTO;
    }
}
