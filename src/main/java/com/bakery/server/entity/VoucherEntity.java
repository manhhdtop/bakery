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
import java.util.Date;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "voucher")
@Where(clause = "deleted is null or deleted = 0")
public class VoucherEntity extends AuditModel {
    private static final long serialVersionUID = 1L;

    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "value")
    private Long value;
    @Column(name = "min_amount")
    private Long minAmount;
    @Column(name = "max_amount")
    private Long maxAmount;
    @Column(name = "min_refund")
    private Long minRefund;
    @Column(name = "max_refund")
    private Long maxRefund;
    @Column(name = "type")
    private Integer type;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "status", columnDefinition = "int default 1")
    private Integer status;
}
