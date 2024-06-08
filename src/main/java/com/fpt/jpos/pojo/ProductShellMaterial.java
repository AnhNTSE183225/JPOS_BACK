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
    @MapsId("shellId")
    @JoinColumn(name = "shell_id")
    private ProductShellDesign productShellDesign;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @MapsId("materialId")
    @JoinColumn(name = "material_id")
    private Material material;

    @JoinColumn(name = "weight")
    private Double weight;

}
