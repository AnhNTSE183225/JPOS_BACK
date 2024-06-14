package com.fpt.jpos.service;

import com.fpt.jpos.dto.Diamond4CDTO;
import com.fpt.jpos.dto.DiamondPriceProjection;
import com.fpt.jpos.pojo.DiamondPrice;

import java.util.List;

public interface IDiamondPriceService {
    public Double getDiamondPricesBy4C(Diamond4CDTO diamond4CDTO);

    public List<DiamondPrice> getDiamondPrices(int pageNo, int pageSize);

    public List<DiamondPriceProjection> getDiamondPrices();
}
