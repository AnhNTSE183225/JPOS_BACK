package com.fpt.jpos.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="product")
@Data
@NoArgsConstructor  // using lombok
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private int productId;

    @Column(name="product_name")
    private String productName;

    private double eDiamondPrice;

    private double eMaterialPrice;

    private double productionPrice;

    private double markupRate;

    private String productType;

    private String productDesignId;



}
