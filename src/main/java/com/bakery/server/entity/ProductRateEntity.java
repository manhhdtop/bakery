package com.bakery.server.entity;

import com.bakery.server.entity.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "product_rate")
@Where(clause = "deleted is null or deleted = 0")
public class ProductRateEntity extends BaseModel {
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "rate")
    private Integer rate;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "description")
    private String description;
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Long createdDate;
}
