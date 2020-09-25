package com.nova.mom.repositories;

import com.nova.mom.entities.DeviceGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceGroupRepository extends JpaRepository<DeviceGroup,Long> {

    Optional<Object> findByCustomer_CustomerIdAndDeviceGroupName(Long customerId, String deviceGroupName);

    Optional<Object> findByCustomer_CustomerIdAndDeviceGroupNameAndDeviceGroupIdNotIn(Long customerId, String deviceGroupName, List<Long> ids);

    Optional<DeviceGroup> findByDeviceGroupId(Long deviceGroupId);
}
