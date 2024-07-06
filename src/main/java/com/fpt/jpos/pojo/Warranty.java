package com.fpt.jpos.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Warranty")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Warranty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warranty_id")
    private Integer warrantyId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "end_of_support_date")
    private Date endOfSupportDate;

    @Column(name = "terms")
    private String terms;
}
