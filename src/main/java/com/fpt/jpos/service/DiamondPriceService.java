package com.fpt.jpos.service;

import com.fpt.jpos.dto.Diamond4CDTO;
import com.fpt.jpos.pojo.DiamondPrice;
import com.fpt.jpos.repository.IDiamondPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DiamondPriceService implements IDiamondPriceService {

    private final IDiamondPriceRepository diamondPriceRepository;

    @Autowired
    public DiamondPriceService(IDiamondPriceRepository diamondPriceRepository) {
        this.diamondPriceRepository = diamondPriceRepository;
    }


    @Override
    public Double getDiamondPricesBy4C(Diamond4CDTO diamond4CDTO) {

        List<DiamondPrice> diamondPriceList = diamondPriceRepository.findDiamondBy4C(diamond4CDTO.getCaratWeight(), diamond4CDTO.getClarity().name(), diamond4CDTO.getColor().name(), diamond4CDTO.getCut().name(), diamond4CDTO.getShape().name());

        Date today = Calendar.getInstance().getTime(); // get today time

        // Sort the list by date in descending order so the most recent date comes first
        diamondPriceList.sort((p1, p2) -> p2.getEffectiveDate().compareTo(p1.getEffectiveDate()));

        double result = 0;

        for (DiamondPrice diamondPrice : diamondPriceList) {
            if (diamondPrice.getEffectiveDate().compareTo(today) <= 0) {
                result = diamondPrice.getPrice();
                break;
            }
        }
        if (diamondPriceList.isEmpty()) {
            throw new RuntimeException("Diamond Price not found");
        }
        return result;
    }

    @Override
    public List<DiamondPrice> getDiamondPrices(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<DiamondPrice> page = diamondPriceRepository.findAll(pageable);
        return page.getContent();
    }
}
