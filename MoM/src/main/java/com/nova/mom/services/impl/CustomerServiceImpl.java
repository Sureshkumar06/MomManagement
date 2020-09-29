package com.nova.mom.services.impl;

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
import com.nova.mom.repositories.ReleaseMasterRepository;
import com.nova.mom.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerMasterRepository customerMasterRepository;

    @Autowired
    private DeviceGroupRepository deviceGroupRepository;

    @Autowired
    private ReleaseMasterRepository releaseMasterRepository;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        LOGGER.info("create customer service method start");
        if(customerDTO !=null){
            if(customerNameUniqueCheck(customerDTO)){
                CustomerMaster customerMaster = new CustomerMaster();
                if(customerDTO.getReleaseId() !=null) {
                    ReleaseMaster releaseMaster = new ReleaseMaster();
                    releaseMaster.setReleaseId(customerDTO.getReleaseId());
                    customerMaster.setReleaseMaster(releaseMaster);
                }
                customerMaster.setCustomerName(customerDTO.getCustomerName());
                customerMaster.setActive("Y");
                customerMaster.setCreatedAt(new Date());
                customerMaster.setCreatedBy("Admin");
                customerMaster.setUpdatedAt(new Date());
                customerMaster.setUpdatedBy("Admin");
                customerMaster.setVersion("V1.0");
                customerMaster.setRecVerNo(1);
                CustomerMaster customerMaster1 = customerMasterRepository.save(customerMaster);
                if(customerMaster1 !=null){
                    List<DeviceGroupDTO> deviceGroupDTOList = new ArrayList<>();
                    DeviceGroup deviceGroup = new DeviceGroup();
                    deviceGroup.setDeviceGroupName("Default");
                    deviceGroup.setCustomerMaster(customerMaster1);
                    deviceGroup.setActive("Y");
                    deviceGroup.setCreatedAt(new Date());
                    deviceGroup.setCreatedBy("Default");
                    deviceGroup.setUpdatedAt(new Date());
                    deviceGroup.setUpdatedBy("Default");
                    deviceGroup.setVersion("V1.0");
                    deviceGroup.setRecVerNo(1);
                    DeviceGroup deviceGroup1 = deviceGroupRepository.save(deviceGroup);
                    DeviceGroupDTO deviceGroupDTO = DeviceGroupMapper.INSTANCE.toDto(deviceGroup1);
                    deviceGroupDTO.setCustomerId(customerMaster1.getCustomerId());
                    deviceGroupDTOList.add(deviceGroupDTO);
                    customerDTO = CustomerMasterMapper.INSTANCE.toDto(customerMaster1);
                    customerDTO.setReleaseId(customerMaster1.getReleaseMaster().getReleaseId());
                    customerDTO.setDeviceGroupDTOList(deviceGroupDTOList);
                }
            }else{
                LOGGER.info("customer name duplicate in create");
                customerDTO.setErrorStatus(true);
                customerDTO.setErrorMessage("Customer Name is already available");
            }
        }
        LOGGER.info("create customer service method end");
        return customerDTO;
    }

    private boolean customerNameUniqueCheck(CustomerDTO customerDTO){
        LOGGER.info("customer name unique check method start");
        boolean uniqueCheck = false;
        if(customerMasterRepository.findByCustomerName(customerDTO.getCustomerName()).isPresent()){
            uniqueCheck = false;
        } else {
            uniqueCheck = true;
        }
        LOGGER.info("customer name unique check method end");
        return uniqueCheck;
    }

    private boolean customerNameUniqueCheckWithId(CustomerDTO customerDTO){
        LOGGER.info("customer name unique check with id method start");
        boolean uniqueCheck = false;
        List<Long> ids = new ArrayList<>();
        ids.add(customerDTO.getCustomerId());
        if(customerMasterRepository.findByCustomerNameAndCustomerIdNotIn(customerDTO.getCustomerName(),ids).isPresent()){
            uniqueCheck = false;
        } else {
            uniqueCheck = true;
        }
        LOGGER.info("customer name unique check with id method end");
        return uniqueCheck;
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        LOGGER.info("update customer service method start");
        CustomerDTO customerDTORes = new CustomerDTO();
        if(customerNameUniqueCheckWithId(customerDTO)) {
            if(customerDTO.getCustomerId() !=null){
                Optional<CustomerMaster> customerMasterOptional = customerMasterRepository.findById(customerDTO.getCustomerId());
                CustomerMaster customerMaster = null;
                if (customerMasterOptional.isPresent()) {
                    customerMaster = customerMasterOptional.get();
                } else {
                    LOGGER.info("customer data not available in update");
                    customerDTORes.setCustomerId(customerDTO.getCustomerId());
                    customerDTORes.setErrorStatus(true);
                    customerDTORes.setErrorMessage("Data Not Available");
                    return customerDTORes;
                }
                customerMaster.setCustomerName(customerDTO.getCustomerName());
//                customerMaster = CustomerMasterMapper.INSTANCE.toEntity(customerDTO);
                if(customerDTO.getReleaseId() !=null) {
                    customerMaster.setReleaseMaster(releaseMasterRepository.findById(customerDTO.getReleaseId()).get());
                }
                if (customerMasterRepository.save(customerMaster) != null) {
                    customerDTORes = getCustomerDetailsById(customerDTO.getCustomerId());
                }
            }
        }else{
            LOGGER.info("customer name duplicate in update");
            customerDTORes.setErrorStatus(true);
            customerDTORes.setErrorMessage("Customer Name is already available");
        }
        LOGGER.info("update customer service method end");
        return customerDTORes;
    }

    @Override
    public List<CustomerDTO> getCustomerDetails() {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        List<CustomerMaster> customerMasterList = customerMasterRepository.findAll();
        if(customerMasterList !=null && !customerMasterList.isEmpty()){
            customerMasterList.stream().map(customer ->{
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO = CustomerMasterMapper.INSTANCE.toDto(customer);
                customerDTO.setReleaseId(customer.getReleaseMaster().getReleaseId());
                customerDTOList.add(customerDTO);
                return null;
            }).collect(Collectors.toList());
        }
        return customerDTOList;
    }

    @Override
    public CustomerDTO getCustomerDetailsById(Long customerId) {
        LOGGER.info("get customer details by id service method start");
        CustomerDTO customerDTO = new CustomerDTO();
        List<DeviceGroupDTO> deviceGroupDTOS = new ArrayList<>();
        if (customerId != null) {
            Optional<CustomerMaster> customerMaster = customerMasterRepository.findByCustomerId(customerId);
            if (customerMaster.isPresent()) {
                if(customerMaster.get().getDeviceGroupList() !=null) {
                    LOGGER.info("device group available for customer");
                    LOGGER.info("device group available for customer"+customerMaster.get().getDeviceGroupList().size());
                    List<DeviceGroup> deviceGroupList = customerMaster.get().getDeviceGroupList();
                    LOGGER.info("device group available for customer"+deviceGroupList.size());

                    deviceGroupList.stream().map(deviceGroup -> {
                        DeviceGroupDTO deviceGroupDTO = new DeviceGroupDTO();
                        deviceGroupDTO = DeviceGroupMapper.INSTANCE.toDto(deviceGroup);
                        deviceGroupDTO.setCustomerId(deviceGroup.getCustomerMaster().getCustomerId());
                        deviceGroupDTOS.add(deviceGroupDTO);
                        return deviceGroupDTOS;
                    }).collect(Collectors.toList());

                }
                customerDTO = CustomerMasterMapper.INSTANCE.toDto(customerMaster.get());
                customerDTO.setReleaseId(customerMaster.get().getReleaseMaster().getReleaseId());
                customerDTO.setDeviceGroupDTOList(deviceGroupDTOS);
            }
        } else {
            LOGGER.info("invalid id for getCustomerDetailsById");
            customerDTO.setErrorMessage("Give Valid Id");
            customerDTO.setErrorStatus(true);
        }
        LOGGER.info("get customer details by id service method end");
        return customerDTO;
    }

}
