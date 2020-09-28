package com.nova.mom.dtos;

import lombok.Data;

@Data
public class DeviceResponseDTO {

    private Long deviceMasterId;

    private String deviceId;

    private boolean status;

    private String message;
}
