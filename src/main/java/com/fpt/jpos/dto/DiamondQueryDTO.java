package com.fpt.jpos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiamondQueryDTO {
    private String origin;
    private List<String> shapeList;
    private List<String> colorList;
    private List<String> clarityList;
    private List<String> cutList;
    private Double minCarat;
    private Double maxCarat;
    private Double minPrice;
    private Double maxPrice;
}
