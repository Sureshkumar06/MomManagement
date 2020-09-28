package com.nova.mom.repositories;

import com.nova.mom.entities.DeviceMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceMasterRepository extends JpaRepository<DeviceMaster,Long> {
    Optional<DeviceMaster> findByDeviceMasterId(Long deviceMasterId);
}
