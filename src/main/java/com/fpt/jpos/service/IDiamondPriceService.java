package com.fpt.jpos.service;

import com.fpt.jpos.dto.DiamondPriceQueryDTO;
import com.fpt.jpos.pojo.DiamondPrice;

import java.util.List;

public interface IDiamondPriceService {

    DiamondPrice addDiamondPrice(DiamondPrice diamondPrice);

    int updateDiamondPrice(DiamondPrice diamondPrice);

    void deletePrice(Integer diamondPriceId);

    List<DiamondPrice> getAllDiamondPrice();

    Double getSingleDiamondPrice(DiamondPriceQueryDTO diamondPriceQueryDTO);
}
