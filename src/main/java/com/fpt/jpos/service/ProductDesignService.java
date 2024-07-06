package com.fpt.jpos.service;

import com.fpt.jpos.pojo.ProductDesign;
import com.fpt.jpos.pojo.ProductShellDesign;
import com.fpt.jpos.repository.IProductDesignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDesignService implements IProductDesignService {

    private final IProductDesignRepository productDesignRepository;

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

    @Override
    public ProductDesign findById(Integer productDesignId) {
        return productDesignRepository.findById(productDesignId).orElseThrow();
    }

    @Override
    public ProductDesign update(ProductDesign productDesign) {
        for(ProductShellDesign shell : productDesign.getProductShellDesigns()) {
            shell.setProductDesign(productDesign);
        }
        return productDesignRepository.save(productDesign);
    }
}
