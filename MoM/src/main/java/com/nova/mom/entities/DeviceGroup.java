package com.nova.mom.entities;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "device_group")
@DynamicUpdate(value = true)
@Data
public class DeviceGroup extends RecordInfo implements Serializable {

    private static final long serialVersionId =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="device_group_id",columnDefinition = "uniqueidentifier")
    private Long deviceGroupId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerMaster customerMaster;

    @Column(name = "device_group_name")
    private String deviceGroupName;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "deviceGroup",orphanRemoval = true,fetch = FetchType.LAZY)
    private List<DeviceMaster> deviceMasterList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {

        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }
}
