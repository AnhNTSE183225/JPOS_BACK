package com.fpt.jpos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticsDTO {
    private Integer noSales;
    private Integer noCustomers;
    private Integer noOrders;
    private Double revenue;
}
