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
    public Double getDiamondPriceBy4C(Diamond4C diamond4C) {

        System.out.println(diamond4C.getCut());
        System.out.println(diamond4C.getCaratWeight());
        System.out.println(diamond4C.getColor());
        System.out.println(diamond4C.getClarity());

        List<DiamondPrice> diamondPriceList = diamondPriceRepository.findDiamondPriceByCaratWeightAndAndClarityAndColorAndCut(
                diamond4C.getCaratWeight(),
                diamond4C.getClarity().name(),
                diamond4C.getColor().name(),
                diamond4C.getCut().name());

        Date currentDate = new Date();

        // Sort the list by date in descending order so the most recent date comes first
        diamondPriceList.sort((p1, p2) -> p2.getEffectiveDate().compareTo(p1.getEffectiveDate()));

        // Find the most recent price that is before the current date
        DiamondPrice mostRecentPrice = diamondPriceList.stream()
                .filter(price -> !price.getEffectiveDate().after(currentDate))
                .findFirst()
                .orElse(null);

        // If there's no such price, return null or an appropriate value
        if (mostRecentPrice == null) {
            return null; // or handle this case as per your business logic
        }

        return mostRecentPrice.getPrice();
    }
}
