package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Diamond4C;
import com.fpt.jpos.pojo.DiamondPrice;

import java.util.List;

public interface IDiamondPriceService {
    public List<DiamondPrice> getDiamondPrices();
    public List<DiamondPrice> getDiamondPricesBy4C(Diamond4C diamond4C);
}
