package com.fpt.jpos.dto;


import lombok.Builder;

public abstract class MaterialPriceDTO {

    @Builder
    public static class MaterialPriceCreateResponse {
        public Integer materialId;
        public Double materialPrice;
    }

    @Builder
    public static class MaterialPriceUpdateResponse {
        public Integer materialId;
        public String effectiveDate;
        public Double materialPrice;
    }
}
