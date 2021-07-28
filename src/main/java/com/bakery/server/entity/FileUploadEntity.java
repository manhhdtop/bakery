package com.bakery.server.entity;

import com.bakery.server.entity.base.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "file_upload")
@Where(clause = "deleted is null or deleted = 0")
public class FileUploadEntity extends AuditModel {
    private static final long serialVersionUID = 1L;

    @Column(name = "reference_id")
    private Long referenceId;
    @Column(name = "reference_type")
    private String referenceType;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "uri")
    private String uri;
    @Column(name = "file_type")
    private String fileType;
    @Column(name = "size")
    private Long size;
}
