package com.fpt.jpos.service;

import com.fpt.jpos.dto.DiamondPriceQueryDTO;
import com.fpt.jpos.dto.ListDiamondPriceQueryDTO;
import com.fpt.jpos.pojo.DiamondPrice;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDiamondPriceService {

    DiamondPrice addDiamondPrice(DiamondPrice diamondPrice);

    int updateDiamondPrice(DiamondPrice diamondPrice);

    void deletePrice(Integer diamondPriceId);

    List<DiamondPrice> getAllDiamondPrice();

    Double getSingleDiamondPrice(DiamondPriceQueryDTO diamondPriceQueryDTO);

    List<DiamondPrice> getDiamondPriceByOriginAndShapeAndCaratRange(String origin, String shape, Double caratFrom, Double caratTo);

    Page<DiamondPrice> getDiamondPricesByQuery(ListDiamondPriceQueryDTO listDiamondPriceQueryDTO, int pageNo, int pageSize);
}
