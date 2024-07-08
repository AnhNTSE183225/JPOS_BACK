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
public class ListDiamondPriceQueryDTO {
    List<String> listOrigin;
    List<String> listShape;
    List<String> listCut;
    List<String> listClarity;
    List<String> listColor;
    Double minCarat;
    Double maxCarat;
}
