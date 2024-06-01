package com.fpt.jpos.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor  // using lombok
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "e_diamond_price")
    private double eDiamondPrice;

    @Column(name = "e_material_price")
    private double eMaterialPrice;

    @Column(name = "production_price")
    private double productionPrice;

    @Column(name = "markup_rate")
    private double markupRate;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "product_design_id")
    private String productDesignId;

    @ManyToMany
    @JoinTable(
            name = "ProductDiamond",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "diamond_id")
    )
    List<Diamond> diamonds = new ArrayList<>();

}
