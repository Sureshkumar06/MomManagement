package com.nova.mom.services.impl;

import com.nova.mom.controllers.DeviceGroupController;
import com.nova.mom.services.DeviceGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeviceGroupServiceImpl implements DeviceGroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceGroupServiceImpl.class);
}
