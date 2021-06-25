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
@Table(name = "email_template")
@Where(clause = "deleted is null or deleted = 0")
public class MailTemplateEntity extends AuditModel {
    private static final long serialVersionUID = 1L;

    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "subject")
    private String subject;
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
    @Column(name = "status", columnDefinition = "int default 1")
    private Integer status;
}
