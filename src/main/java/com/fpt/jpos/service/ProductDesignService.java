package com.fpt.jpos.service;

import com.fpt.jpos.pojo.ProductDesign;
import com.fpt.jpos.repository.IProductDesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDesignService implements IProductDesignService {

    @Autowired
    private IProductDesignRepository productDesignRepository;

    @Override
    public List<ProductDesign> getProductDesigns() {
        List<ProductDesign> productDesigns = productDesignRepository.findAll();
        if (!productDesigns.isEmpty()) {
            return productDesigns;
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public List<ProductDesign> getProductDesignsByCategory(String designType) {
        List<ProductDesign> productDesigns = productDesignRepository.findByDesignType(designType);
        if (!productDesigns.isEmpty()) {
            return productDesigns;
        } else {
            return new ArrayList<>();
        }
    }
}
