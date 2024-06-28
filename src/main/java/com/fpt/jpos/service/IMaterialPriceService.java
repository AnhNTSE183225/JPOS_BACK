package com.fpt.jpos.service;

public interface IMaterialPriceService {

    Double getLatestPriceById(Integer id);

    boolean addMaterialPrice(Integer materialId, Double materialPrice);

    boolean updateMaterialPrice(Double materialPrice, Integer materialId, String effectiveDate);

}
