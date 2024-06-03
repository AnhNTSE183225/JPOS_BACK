package com.fpt.jpos.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ProductShellMaterial")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductShellMaterial {

    @EmbeddedId
    @JsonIgnore
    private ProductShellMaterialId id;

    @JsonIgnore
    @ManyToOne
    @MapsId("productShellDesignId")
    @JoinColumn(name = "product_shell_design_id")
    private ProductShellDesign productShellDesign;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @MapsId("materialId")
    @JoinColumn(name = "material_id")
    private Material material;

    private double weight;

}
