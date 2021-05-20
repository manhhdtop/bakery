package com.bakery.server.entity;

import com.bakery.server.entity.base.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "product_option")
@Where(clause = "deleted is null or deleted = 0")
public class ProductOptionEntity extends AuditModel {
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "value")
    private String value;
    @ManyToOne
    @JoinColumn(name = "option_type", referencedColumnName = "id")
    private OptionTypeEntity optionType;
    @Column(name = "more_info")
    private String moreInfo;
}
