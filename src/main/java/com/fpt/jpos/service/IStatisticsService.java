package com.fpt.jpos.service;

import com.fpt.jpos.dto.StatisticsDTO;
import com.fpt.jpos.pojo.Product;

import java.util.List;

public interface IStatisticsService {
    StatisticsDTO getStatistics();
    List<Integer> getSalesReport();

    List<Product> getRecentlyPurchased();

    List<Object[]> getPaymentByDate();
}
