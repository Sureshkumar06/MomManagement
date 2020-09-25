package com.nova.mom.entities;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@DynamicUpdate(value = true)
@Data
public class RecordInfo implements Serializable {

    @Column(name = "version")
    private String version = "V1.0";

    @Column(name = "active")
    @Size(max=1)
    private String active = "Y";

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "created_by")
    private String createdBy = "admin";

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @Column(name = "updated_by")
    private String updatedBy = "admin";

    @Column(name = "rec_version")
    private Integer recVerNo = 1;
}
