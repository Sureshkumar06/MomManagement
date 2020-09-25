package com.nova.mom.services;

import com.nova.mom.dtos.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    List<CustomerDTO> getCustomerDetails();

    CustomerDTO getCustomerDetailsById(Long customerId);
}
