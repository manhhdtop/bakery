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
@Table(name = "invoice")
@Where(clause = "deleted is null or deleted = 0")
public class InvoiceEntity extends AuditModel {
    private static final long serialVersionUID = 1L;

    @Column(name = "invoice_id", nullable = false, unique = true)
    private String invoiceId;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_email")
    private String customerEmail;
    @Column(name = "customer_phone")
    private String customerPhone;
    @OneToOne
    @JoinColumn(name = "province_id", referencedColumnName = "id")
    private CatalogEntity province;
    @OneToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private CatalogEntity district;
    @Column(name = "address")
    private String address;
    @OneToOne
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    private VoucherEntity voucher;
    @Column(name = "status")
    private Integer status;
    @Column(name = "status_description")
    private String statusDescription;
    @Column(name = "total_amount")
    private Long totalAmount;
    @OneToMany
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private List<InvoiceProductEntity> products;
}
