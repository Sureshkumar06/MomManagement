package com.nova.mom.dtos;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

@Data
public class CustomerDTO {

    private Long customerId;

    private String customerName;

    private Long releaseId;

    private String active;

    private String version;

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String updatedBy;

    private String errorMessage;

    private boolean errorStatus;

    private Integer recVerNo;

    private List<DeviceGroupDTO> deviceGroupDTOList;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
