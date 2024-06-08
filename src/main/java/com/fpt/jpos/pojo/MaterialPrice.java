package com.fpt.jpos.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MaterialPriceList")
public class MaterialPrice {

    @EmbeddedId
    private MaterialPriceId materialPriceId;

    @Column(name = "price")
    private Double price;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @MapsId("materialId")
    @JoinColumn(name = "material_id")
    private Material material;
}
