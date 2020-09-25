package com.nova.mom.controllers;

import com.nova.mom.dtos.CustomerDTO;
import com.nova.mom.services.CustomerService;
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
    CustomerService customerService;

    @PostMapping(MoMConstants.CUSTOMER_DETAILS)
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO responseDto = customerService.createCustomer(customerDTO);
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping(value = MoMConstants.CUSTOMER_DETAILS)
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO responseDto = customerService.updateCustomer(customerDTO);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(MoMConstants.CUSTOMER_DETAILS)
    public ResponseEntity<List<CustomerDTO>> getCustomerDetails() {
        List<CustomerDTO> responseDto = customerService.getCustomerDetails();
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(value = MoMConstants.CUSTOMER_DETAILS + MoMConstants.CUSTOMER_ID)
    public ResponseEntity<CustomerDTO> getCustomerDetailsById(@PathVariable Long customerId) {
        CustomerDTO responseDto = customerService.getCustomerDetailsById(customerId);
        return ResponseEntity.ok().body(responseDto);
    }

}
