package com.fpt.jpos.service;

import com.fpt.jpos.pojo.ProductShellDesign;
import com.fpt.jpos.repository.IProductShellDesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductShellDesignService implements IProductShellDesignService {

    private final IProductShellDesignRepository productShellDesignRepository;

    @Autowired
    public ProductShellDesignService(IProductShellDesignRepository productShellDesignRepository) {
        this.productShellDesignRepository = productShellDesignRepository;
    }


    @Override
    public List<ProductShellDesign> getByProductDesignId(Integer productDesignId) {
        return productShellDesignRepository.findByProductDesignId(productDesignId);
    }
}
