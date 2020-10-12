package com.nova.mom.entities;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customer_master")
@DynamicUpdate(value = true)
@Data
public class CustomerMaster extends RecordInfo implements Serializable {

    private static final long serialVersionId =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id",columnDefinition = "uniqueidentifier")
    private Long customerId;

    @ManyToOne
    @JoinColumn(name = "release_id")
    private ReleaseMaster releaseMaster;

    @Column(name = "customer_name")
    private String customerName;

    @Type(type="uuid-char")
    @Column(name = "customer_mapper_id")
    private UUID customerMapperId;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customerMaster",orphanRemoval = true,fetch = FetchType.LAZY)
    private List<DeviceGroup> deviceGroupList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {

        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }
}
