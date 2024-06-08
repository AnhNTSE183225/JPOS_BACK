package com.fpt.jpos.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductMaterialId implements Serializable {
    @Column(name = "product_id")
    private int productId;

    @Column(name = "material_id")
    private int materialId;
}
