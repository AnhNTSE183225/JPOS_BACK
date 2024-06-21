package com.fpt.jpos.service;

import com.fpt.jpos.dto.DiamondPriceQueryDTO;
import com.fpt.jpos.pojo.DiamondPrice;

import java.util.List;

public interface IDiamondPriceService {

    DiamondPrice addDiamondPrice(DiamondPrice diamondPrice);
    DiamondPrice updateDiamondPrice(Integer diamondPriceId, Double newPrice);
    void deletePrice(Integer diamondPriceId);
    List<DiamondPrice> getAllDiamondPrice(int pageNo, int pageSize);
    Double getSingleDiamondPrice(DiamondPriceQueryDTO diamondPriceQueryDTO);
}
