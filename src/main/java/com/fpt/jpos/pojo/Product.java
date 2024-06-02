package com.fpt.jpos.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "ProductDiamond",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "diamond_id")
    )
    List<Diamond> diamonds = new ArrayList<>();

//    @ManyToMany
//    @JoinTable(
//            name = "ProductMaterial",
//            joinColumns = @JoinColumn(name = "product_id"),
//            inverseJoinColumns = @JoinColumn(name = "material_id")
//    )
//    List<Material> materials = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductMaterial> materials;

}
