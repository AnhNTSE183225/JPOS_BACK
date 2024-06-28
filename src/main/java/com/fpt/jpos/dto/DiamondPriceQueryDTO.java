package com.fpt.jpos.dto;

import com.fpt.jpos.pojo.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiamondPriceQueryDTO {
    private Origin origin;
    private Shape shape;
    private Double caratWeight;
    private Color color;
    private Clarity clarity;
    private Cut cut;
}
