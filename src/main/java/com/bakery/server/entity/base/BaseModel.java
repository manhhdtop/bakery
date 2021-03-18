package com.bakery.server.entity.base;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "deleted")
    private Integer deleted = 0;
}
