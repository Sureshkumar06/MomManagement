package com.nova.mom.entities;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "device_master")
@DynamicUpdate(value = true)
@Data
public class DeviceMaster extends RecordInfo implements Serializable {

    private static final long serialVersionId =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="device_master_id",columnDefinition = "uniqueidentifier")
    private Long deviceMasterId;

    @ManyToOne
    @JoinColumn(name = "device_group_id")
    private DeviceGroup deviceGroup;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "device_id")
    private String deviceId;

    @Override
    public boolean equals(Object o) {

        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }
}
