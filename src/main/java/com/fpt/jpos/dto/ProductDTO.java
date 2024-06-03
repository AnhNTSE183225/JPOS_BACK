package com.fpt.jpos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private String productName;
    private String productType;
    private Double eMaterialPrice;
    private Double eDiamondPrice;
    private Double productionPrice;
    private Double markupRate;
    private List<Integer> diamondIds;
    private List<Pair<Integer,Double>> materialsIds;
}
