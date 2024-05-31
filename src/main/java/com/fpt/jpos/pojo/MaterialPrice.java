package com.fpt.jpos.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "MaterialPriceList")
public class MaterialPrice {

    @EmbeddedId
    private MaterialPriceId id;

    @Column(name = "price")
    private Double price;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @MapsId("materialId")
    @JoinColumn(name = "material_id")
    private Material material;
}
