package com.bakery.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "invoice_product")
public class InvoiceProductEntity {
    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "invoice_id")
    private Long invoiceId;
    @OneToOne
    private ProductEntity product;
    @Column(name = "quantity")
    private Integer quantity;
}
