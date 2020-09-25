package com.nova.mom.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class DeviceGroupDTO {

    private Long deviceGroupId;

    private String deviceGroupName;

    private String active;

    private String version;

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String updatedBy;

    private String errorMessage;

    private boolean errorStatus;

    private Integer recVerNo;

}
