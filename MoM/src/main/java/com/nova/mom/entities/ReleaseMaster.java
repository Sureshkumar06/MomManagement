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
@Table(name = "release_master")
@DynamicUpdate(value = true)
@Data
public class ReleaseMaster extends RecordInfo implements Serializable {

    private static final long serialVersionId =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="release_id",columnDefinition = "uniqueidentifier")
    private Long releaseId;

    @Column(name = "release_name")
    private String releaseName;

    @Column(name = "server_base_url")
    private String serverBaseUrl;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "releaseMaster",orphanRemoval = true,fetch = FetchType.LAZY)
    private List<CustomerMaster> customerMasterList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {

        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }
}
