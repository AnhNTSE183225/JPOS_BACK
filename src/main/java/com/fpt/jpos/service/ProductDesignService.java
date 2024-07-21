package com.fpt.jpos.service;

import com.fpt.jpos.pojo.DesignConfiguration;
import com.fpt.jpos.pojo.ProductDesign;
import com.fpt.jpos.pojo.ProductShellDesign;
import com.fpt.jpos.pojo.ProductShellMaterial;
import com.fpt.jpos.repository.IDesignConfigurationRepository;
import com.fpt.jpos.repository.IProductDesignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDesignService implements IProductDesignService {

    private final IProductDesignRepository productDesignRepository;
    private final IDesignConfigurationRepository designConfigurationRepository;

    //Binh
    @Override
    public List<ProductDesign> getProductDesigns() {
        List<ProductDesign> productDesigns = productDesignRepository.findAll();
        if (!productDesigns.isEmpty()) {
            return productDesigns;
        } else {
            return new ArrayList<>();
        }

    }

    //Binh
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
        for (ProductShellDesign shell : productDesign.getProductShellDesigns()) {
            shell.setProductDesign(productDesign);
            for (ProductShellMaterial productShellMaterial : shell.getProductShellMaterials()) {
                productShellMaterial.setProductShellDesign(shell);
                //System.out.println("Material ID: " + material.getId());
            }
            //System.out.println("Shell ID: " + shell.getProductShellDesignId());
        }
        //System.out.println("ProductDesign ID: " + productDesign.getProductDesignId());
        return productDesignRepository.save(productDesign);
    }

    @Override
    public ProductDesign add(ProductDesign productDesign) {
        return productDesignRepository.save(productDesign);
    }

    @Override
    public List<DesignConfiguration> findByDesignType(String designType) {
        return this.designConfigurationRepository.findDesignConfigurationByDesignType(designType);
    }

    @Override
    public void delete(Integer productDesignId) {
        ProductDesign productDesign = this.productDesignRepository.findById(productDesignId).orElseThrow();
        this.productDesignRepository.delete(productDesign);
    }

}
