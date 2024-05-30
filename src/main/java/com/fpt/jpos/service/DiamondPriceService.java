package com.fpt.jpos.service;

import com.fpt.jpos.pojo.DiamondPrice;
import com.fpt.jpos.repository.IDiamondPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
