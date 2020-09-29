package com.nova.mom.services.impl;

import com.nova.mom.controllers.DeviceGroupController;
import com.nova.mom.dtos.CustomerDTO;
import com.nova.mom.dtos.DeviceGroupDTO;
import com.nova.mom.dtos.ReleaseDTO;
import com.nova.mom.entities.CustomerMaster;
import com.nova.mom.entities.DeviceGroup;
import com.nova.mom.entities.ReleaseMaster;
import com.nova.mom.mappers.CustomerMasterMapper;
import com.nova.mom.mappers.DeviceGroupMapper;
import com.nova.mom.mappers.ReleaseMasterMapper;
import com.nova.mom.repositories.CustomerMasterRepository;
import com.nova.mom.repositories.DeviceGroupRepository;
import com.nova.mom.services.DeviceGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeviceGroupServiceImpl implements DeviceGroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceGroupServiceImpl.class);

    @Autowired
    private DeviceGroupRepository deviceGroupRepository;

    @Autowired
    private CustomerMasterRepository customerMasterRepository;

    @Override
    public DeviceGroupDTO createDeviceGroup(DeviceGroupDTO deviceGroupDTO) {
        LOGGER.info("create device group service method start");
        if(deviceGroupDTO !=null){
            if(deviceGroupNameUniqueCheck(deviceGroupDTO)){
                DeviceGroup deviceGroup = new DeviceGroup();
                CustomerMaster customerMaster = new CustomerMaster();
                customerMaster.setCustomerId(deviceGroupDTO.getCustomerId());
                deviceGroup.setCustomerMaster(customerMaster);
                deviceGroup.setDeviceGroupName(deviceGroupDTO.getDeviceGroupName());
                deviceGroup.setActive("Y");
                deviceGroup.setCreatedAt(new Date());
                deviceGroup.setCreatedBy("Admin");
                deviceGroup.setUpdatedAt(new Date());
                deviceGroup.setUpdatedBy("Admin");
                deviceGroup.setVersion("V1.0");
                deviceGroup.setRecVerNo(1);
                DeviceGroup deviceGroup1 = deviceGroupRepository.save(deviceGroup);
                deviceGroupDTO = DeviceGroupMapper.INSTANCE.toDto(deviceGroup1);
            }else{
                LOGGER.info("release name duplicate in create");
                deviceGroupDTO.setErrorStatus(true);
                deviceGroupDTO.setErrorMessage("Release Name is already available");
            }
        }
        LOGGER.info("create release service method end");
        return deviceGroupDTO;
    }

    private boolean deviceGroupNameUniqueCheck(DeviceGroupDTO deviceGroupDTO){
        LOGGER.info("device group name unique check method start");
        boolean uniqueCheck = false;
        if(deviceGroupRepository.findByCustomerMaster_CustomerIdAndDeviceGroupName(deviceGroupDTO.getCustomerId(),deviceGroupDTO.getDeviceGroupName()).isPresent()){
            uniqueCheck = false;
        } else {
            uniqueCheck = true;
        }
        LOGGER.info("device group name unique check method end");
        return uniqueCheck;
    }

    private boolean deviceGroupNameUniqueCheckWithId(DeviceGroupDTO deviceGroupDTO){
        LOGGER.info("device group name unique check with id method start");
        boolean uniqueCheck = false;
        List<Long> ids = new ArrayList<>();
        ids.add(deviceGroupDTO.getDeviceGroupId());
        if(deviceGroupRepository.findByCustomerMaster_CustomerIdAndDeviceGroupNameAndDeviceGroupIdNotIn(deviceGroupDTO.getCustomerId(),deviceGroupDTO.getDeviceGroupName(),ids).isPresent()){
            uniqueCheck = false;
        } else {
            uniqueCheck = true;
        }
        LOGGER.info("device group name unique check with id method end");
        return uniqueCheck;
    }

    @Override
    public DeviceGroupDTO updateDeviceGroup(DeviceGroupDTO deviceGroupDTO) {
        LOGGER.info("update device group service method start");
        DeviceGroupDTO deviceGroupDTORes = new DeviceGroupDTO();
        if(deviceGroupNameUniqueCheckWithId(deviceGroupDTO)) {
            if(deviceGroupDTO.getDeviceGroupId() !=null){
                Optional<DeviceGroup> deviceGroupOptional = deviceGroupRepository.findById(deviceGroupDTO.getDeviceGroupId());
                DeviceGroup deviceGroup = null;
                if (deviceGroupOptional.isPresent()) {
                    deviceGroup = deviceGroupOptional.get();
                } else {
                    LOGGER.info("device group data not available in update");
                    deviceGroupDTORes.setDeviceGroupId(deviceGroupDTO.getDeviceGroupId());
                    deviceGroupDTORes.setErrorStatus(true);
                    deviceGroupDTORes.setErrorMessage("Data Not Available");
                    return deviceGroupDTORes;
                }
                deviceGroup = DeviceGroupMapper.INSTANCE.toEntity(deviceGroupDTO);
                if(deviceGroupDTO.getCustomerId() !=null) {
                    deviceGroup.setCustomerMaster(customerMasterRepository.findById(deviceGroupDTO.getCustomerId()).get());
                }
                if (deviceGroupRepository.save(deviceGroup) != null) {
                    deviceGroupDTORes = getDeviceGroupDetailsById(deviceGroupDTO.getDeviceGroupId());
                }
            }
        }else{
            LOGGER.info("device group name duplicate in update");
            deviceGroupDTORes.setErrorStatus(true);
            deviceGroupDTORes.setErrorMessage("device group Name is already available");
        }
        LOGGER.info("update device group service method end");
        return deviceGroupDTORes;
    }

    @Override
    public List<DeviceGroupDTO> getDeviceGroupDetails() {
        List<DeviceGroupDTO> deviceGroupDTOList = new ArrayList<>();
        List<DeviceGroup> deviceGroupList = deviceGroupRepository.findAll();
        if(deviceGroupList !=null && !deviceGroupList.isEmpty()){
            deviceGroupList.stream().map(deviceGroup ->{
                DeviceGroupDTO deviceGroupDTO = new DeviceGroupDTO();
                deviceGroupDTO = DeviceGroupMapper.INSTANCE.toDto(deviceGroup);
                deviceGroupDTO.setCustomerId(deviceGroup.getCustomerMaster().getCustomerId());
                deviceGroupDTOList.add(deviceGroupDTO);
                return null;
            }).collect(Collectors.toList());
        }
        return deviceGroupDTOList;
    }

    @Override
    public DeviceGroupDTO getDeviceGroupDetailsById(Long deviceGroupId) {
        LOGGER.info("get device group details by id service method start");
        DeviceGroupDTO deviceGroupDTO = new DeviceGroupDTO();
        if(deviceGroupId !=null){
            Optional<DeviceGroup> deviceGroup = deviceGroupRepository.findByDeviceGroupId(deviceGroupId);
            if(deviceGroup.isPresent()){
                deviceGroupDTO = DeviceGroupMapper.INSTANCE.toDto(deviceGroup.get());
                deviceGroupDTO.setCustomerId(deviceGroup.get().getCustomerMaster().getCustomerId());
            }
        }else{
            LOGGER.info("invalid id for getDeviceGroupDetailsById");
            deviceGroupDTO.setErrorMessage("Give Valid Id");
            deviceGroupDTO.setErrorStatus(true);
        }
        LOGGER.info("get device group details by id service method end");
        return deviceGroupDTO;
    }
}
