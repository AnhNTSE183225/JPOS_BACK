package com.fpt.jpos.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Material")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private int materialId;

    @Column(name = "material_name")
    private String materialName;

    @JsonIgnore
    @OneToMany(mappedBy = "material",
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<MaterialPrice> materialPrices;

    @JsonIgnore
    @OneToMany(mappedBy = "material")
    private List<ProductMaterial> productMaterials;
}
