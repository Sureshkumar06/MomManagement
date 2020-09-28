package com.nova.mom.entities;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_master")
@DynamicUpdate(value = true)
@Data
public class UserMaster extends RecordInfo implements Serializable {

    private static final long serialVersionId =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id",columnDefinition = "uniqueidentifier")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "device_master_id")
    private DeviceMaster deviceMaster;

    @ManyToOne
    @JoinColumn(name = "user_group_id")
    private UserGroup userGroup;

    @Column(name = "user_name")
    private String userName;

    @Override
    public boolean equals(Object o) {

        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }
}
