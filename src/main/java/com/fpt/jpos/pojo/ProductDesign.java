package com.fpt.jpos.pojo;

import com.fpt.jpos.pojo.enums.DesignType;
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
    @Enumerated(EnumType.STRING)
    private DesignType designType;

    @Column(name = "design_file")
    private String designFile;

    @OneToMany(
            mappedBy = "productDesign",
            cascade = CascadeType.ALL
    )
    private List<ProductShellDesign> productShellDesigns;

}
