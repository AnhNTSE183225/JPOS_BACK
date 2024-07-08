package com.fpt.jpos.service;

import com.fpt.jpos.dto.StatisticsDTO;

import java.util.List;

public interface IStatisticsService {
    StatisticsDTO getStatistics();
    List<Integer> getSalesReport();
}
