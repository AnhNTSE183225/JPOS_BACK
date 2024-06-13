package com.fpt.jpos.dto;

import com.fpt.jpos.pojo.enums.Clarity;
import com.fpt.jpos.pojo.enums.Color;
import com.fpt.jpos.pojo.enums.Cut;
import com.fpt.jpos.pojo.enums.Shape;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiamondPriceDisplayDTO {
    private Color color;
    private Clarity clarity;
    private Cut cut;
    private String caratWeightRange;
    private Shape shape;
    private Double price;
}
