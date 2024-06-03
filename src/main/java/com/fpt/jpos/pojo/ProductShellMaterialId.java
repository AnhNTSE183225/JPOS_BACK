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
public class ProductShellMaterialId implements Serializable {
    @Column(name = "product_shell_design_id")
    private int productShelDesignId;

    @Column(name = "material_id")
    private int materialId;
}
