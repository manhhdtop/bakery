package com.bakery.server.entity;

import com.bakery.server.entity.base.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "product")
@Where(clause = "deleted is null or deleted = 0")
public class ProductEntity extends AuditModel {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @OneToMany
    private List<FileUploadEntity> images;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;
    @Column(name = "status")
    private Integer status;
}
