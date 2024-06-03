package com.fpt.jpos.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "ProductDesign")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDesign {

    @Id
    @Column(name = "product_design_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productDesignId;

    @Column(name = "design_name")
    private String designName;

    @Column(name = "design_type")
    private String designType;

    @OneToMany(
            mappedBy = "productDesign"
    )
    @JoinColumn(name = "product_design_id")
    private List<ProductShellDesign> productShellDesigns;


}
