package com.fpt.jpos.service;

import com.fpt.jpos.dto.Diamond4CDTO;
import com.fpt.jpos.pojo.DiamondPrice;
import com.fpt.jpos.repository.IDiamondPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiamondPriceService implements IDiamondPriceService {

    private final IDiamondPriceRepository diamondPriceRepository;

    @Autowired
    public DiamondPriceService(IDiamondPriceRepository diamondPriceRepository) {
        this.diamondPriceRepository = diamondPriceRepository;
    }

    @Override
    public List<DiamondPrice> getDiamondPrices() {
        return diamondPriceRepository.findAll();
    }

    @Override
    public List<DiamondPrice> getDiamondPricesBy4C(Diamond4CDTO diamond4CDTO) {

        List<DiamondPrice> diamondPriceList = diamondPriceRepository.findDiamondPriceByCaratWeightAndAndClarityAndColorAndCut(
                diamond4CDTO.getFromCaratWeight(),
                diamond4CDTO.getToCaratWeight(),
                diamond4CDTO.getClarity().name(),
                diamond4CDTO.getColor().name(),
                diamond4CDTO.getCut().name());

        // Sort the list by date in descending order so the most recent date comes first
        diamondPriceList.sort((p1, p2) -> p2.getEffectiveDate().compareTo(p1.getEffectiveDate()));

        return diamondPriceList;
    }
}
