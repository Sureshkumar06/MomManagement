package com.nova.mom.repositories;

import com.nova.mom.entities.CustomerMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerMasterRepository extends JpaRepository<CustomerMaster,Long> {
    Optional<Object> findByCustomerName(String customerName);

    Optional<Object> findByCustomerNameAndCustomerIdNotIn(String customerName, List<Long> ids);

    Optional<CustomerMaster> findByCustomerId(Long customerId);
}
