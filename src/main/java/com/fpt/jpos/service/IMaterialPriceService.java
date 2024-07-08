package com.fpt.jpos.service;

import com.fpt.jpos.pojo.MaterialPrice;

import java.util.List;

public interface IMaterialPriceService {

    Double getLatestPriceById(Integer id);

    boolean addMaterialPrice(Integer materialId, Double materialPrice);

    boolean updateMaterialPrice(Double materialPrice, Integer materialId, String effectiveDate);

    List<MaterialPrice> findAll();
}
