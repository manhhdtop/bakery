package com.bakery.server.entity.base;

import com.bakery.server.entity.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class AuditModel extends BaseModel {
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Long createdDate;
    @ManyToOne
    @JoinColumn(name = "created_by")
    @CreatedBy
    private UserEntity createdBy;
    @Column(name = "updated_date")
    @LastModifiedDate
    private Long updatedDate;
    @ManyToOne
    @JoinColumn(name = "updated_by")
    @LastModifiedBy
    private UserEntity updatedBy;
}
