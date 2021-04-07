package com.bakery.server.entity;

import com.bakery.server.constant.Status;
import com.bakery.server.entity.base.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "category")
@Where(clause = "deleted is null or deleted = 0")
public class CategoryEntity extends AuditModel {
    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "status", columnDefinition = "int default 1")
    private Integer status;
    @ManyToOne
    private CategoryEntity parent;
}
