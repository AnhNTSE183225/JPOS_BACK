package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Warranty;
import com.fpt.jpos.repository.IWarrantyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarrantyService implements IWarrantyService{

    private final IWarrantyRepository warrantyRepository;

    @Override
    public Warranty getWarrantyByProductId(Integer productId) {
        return warrantyRepository.findByProductId(productId);
    }
}
