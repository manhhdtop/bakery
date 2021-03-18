package com.bakery.server.entity;

import com.bakery.server.entity.base.AuditModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "role")
@Where(clause = "deleted is null or deleted = 0")
public class RoleEntity extends AuditModel {
    private static final long serialVersionUID = 1L;

    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "name")
    private String name;
}
