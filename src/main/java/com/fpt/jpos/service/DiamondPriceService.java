package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Diamond4C;
import com.fpt.jpos.pojo.DiamondPrice;
import com.fpt.jpos.repository.IDiamondPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiamondPriceService implements IDiamondPriceService {

    private IDiamondPriceRepository diamondPriceRepository;

    @Autowired
    public DiamondPriceService(IDiamondPriceRepository diamondPriceRepository) {
        this.diamondPriceRepository = diamondPriceRepository;
    }

    @Override
    public List<DiamondPrice> getDiamondPrices() {
        return diamondPriceRepository.findAll();
    }

    @Override
    public List<DiamondPrice> getDiamondPricesBy4C(Diamond4C diamond4C) {

        List<DiamondPrice> diamondPriceList = diamondPriceRepository.findDiamondPriceByCaratWeightAndAndClarityAndColorAndCut(
                diamond4C.getFromCaratWeight(),
                diamond4C.getToCaratWeight(),
                diamond4C.getClarity().name(),
                diamond4C.getColor().name(),
                diamond4C.getCut().name());

        // Sort the list by date in descending order so the most recent date comes first
        diamondPriceList.sort((p1, p2) -> p2.getEffectiveDate().compareTo(p1.getEffectiveDate()));

        return diamondPriceList;
    }
}
