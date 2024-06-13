package com.fpt.jpos.service;

import com.fpt.jpos.dto.Diamond4CDTO;
import com.fpt.jpos.dto.DiamondPriceDisplayDTO;
import com.fpt.jpos.pojo.Diamond;
import com.fpt.jpos.pojo.DiamondPrice;
import com.fpt.jpos.pojo.enums.Clarity;
import com.fpt.jpos.pojo.enums.Color;
import com.fpt.jpos.pojo.enums.Cut;
import com.fpt.jpos.pojo.enums.Shape;
import com.fpt.jpos.repository.IDiamondPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<DiamondPrice> getDiamondPrices() {
//        Double maxPrice = diamondPriceRepository.getMaximumCaratWeight();
//        List<DiamondPriceDisplayDTO> result = new ArrayList<>();
//        double carat = 0.0;
//
//        while(carat < maxPrice) {
//
//            for(Color color : Color.values()) {
//                for(Clarity clarity : Clarity.values()) {
//                    for(Cut cut : Cut.values()) {
//                        for(Shape shape: Shape.values()) {
//                            Double price = diamondPriceRepository.findDiamondBy4CInRange(carat,carat+0.2,clarity.name(),color.name(),cut.name(),shape.name());
//                            if(price != null && price > 0) {
//                                DiamondPriceDisplayDTO priceListing = new DiamondPriceDisplayDTO(color, clarity, cut, String.format("%s - %s",carat,carat+0.2),shape,price);
//                                result.add(priceListing);
//                            }
//                        }
//                    }
//                }
//            }
//
//            carat += 0.2;
//        }
        return diamondPriceRepository.findAll();
    }
}
