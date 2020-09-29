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
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerService customerService;

    @PostMapping(MoMConstants.CUSTOMER_DETAILS)
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        LOGGER.info("create customer method start");
        CustomerDTO responseDto = customerService.createCustomer(customerDTO);
        LOGGER.info("create customer method end");
        return ResponseEntity.ok().body(responseDto);
    }

    @PutMapping(MoMConstants.CUSTOMER_DETAILS)
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        LOGGER.info("update customer method start");
        CustomerDTO responseDto = customerService.updateCustomer(customerDTO);
        LOGGER.info("update customer method end");
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(MoMConstants.CUSTOMER_DETAILS)
    public ResponseEntity<List<CustomerDTO>> getCustomerDetails() {
        LOGGER.info("get customer method start");
        List<CustomerDTO> responseDto = customerService.getCustomerDetails();
        LOGGER.info("get customer method end");
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping(MoMConstants.CUSTOMER_DETAILS + MoMConstants.CUSTOMER_ID)
    public ResponseEntity<CustomerDTO> getCustomerDetailsById(@PathVariable Long customerId) {
        LOGGER.info("get by id customer method start");
        CustomerDTO responseDto = customerService.getCustomerDetailsById(customerId);
        LOGGER.info("get by id customer method end");
        return ResponseEntity.ok().body(responseDto);
    }
}
