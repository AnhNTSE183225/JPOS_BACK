package com.fpt.jpos.service;

import com.fpt.jpos.pojo.DesignConfiguration;
import com.fpt.jpos.pojo.ProductDesign;

import java.util.List;

public interface IProductDesignService {

    List<ProductDesign> getProductDesigns();

    List<ProductDesign> getProductDesignsByCategory(String category);

    ProductDesign findById(Integer productDesignId);

    ProductDesign update(ProductDesign productDesign);

    ProductDesign add(ProductDesign productDesign);

    List<DesignConfiguration> findByDesignType(String designType);

    void delete(Integer productDesignId);
}
