package com.fpt.jpos.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ProductShellDesign")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductShellDesign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_shell_design_id")
    private int productShellDesignId;

    @Column(name = "shell_name")
    private String shellName;

    @Column(name = "diamond_quantity")
    private int diamondQuantity;

    @Column(name = "e_diamond_price")
    private Double eDiamondPrice;

    @Column(name = "e_material_price")
    private Double eMaterialPrice;

    @Column(name = "production_price")
    private Double productionPrice;

    @ManyToOne
    @JoinColumn(name = "product_design_id")
    private ProductDesign productDesign;

}
