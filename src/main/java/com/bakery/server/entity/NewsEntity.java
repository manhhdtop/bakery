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
@Table(name = "news")
@Where(clause = "deleted is null or deleted = 0")
public class NewsEntity extends AuditModel {
    private static final long serialVersionUID = 1L;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;
    @Column(name = "name")
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @OneToOne
    @JoinColumn(name = "reference_id", referencedColumnName = "id")
    private FileUploadEntity image;
    @Column(name = "_read", columnDefinition = "int default 0")
    private Integer read;
    @Column(name = "_like", columnDefinition = "int default 0")
    private Integer like;
    @Column(name = "status", columnDefinition = "int default 1")
    private Integer status;
}
