package com.fpt.jpos.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialPriceId implements Serializable {

    @Column(name = "material_id")
    private Integer materialId;

    @Column(name = "effective_date")
    @Temporal(TemporalType.DATE)
    private Date effectiveDate;
}
