package com.fpt.jpos.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "[Order]")
@Data
@NoArgsConstructor  // using lombok
@AllArgsConstructor
public class Order {

    // declare entity fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "product_id")
    private Product product;

    private String status;

    private Date orderDate;

    private String orderType;

    private String budget;

    private String designFile;

    private String description;

    private double qDiamondPrice;

    private double qMaterialPrice;

    private Date qDate;

    private double oDiamondPrice;

    private double oMaterialPrice;

    private Date oDate;

    private String modelFile;

    private String modelFeedback;

    private String productImage;

    private double shippingFee;

    private double taxFee;

    private double discount;



}
