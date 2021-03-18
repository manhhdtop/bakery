package com.bakery.server.entity;

import com.bakery.server.constant.UserStatus;
import com.bakery.server.entity.base.AuditModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
@Where(clause = "deleted is null or deleted = 0")
public class UserEntity extends AuditModel {
    private static final long serialVersionUID = 1L;

    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "status", columnDefinition = "int(1) default 1")
    private UserStatus userStatus;
    @OneToMany
    private List<RoleEntity> roles;
}
