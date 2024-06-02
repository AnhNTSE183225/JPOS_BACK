package com.fpt.jpos.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ProductMaterialId implements Serializable {
    @Column(name = "product_id")
    private int productId;

    @Column(name = "material_id")
    private int materialId;
}
