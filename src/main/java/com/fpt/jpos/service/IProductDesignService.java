package com.fpt.jpos.service;

import com.fpt.jpos.pojo.ProductDesign;

import java.util.List;

public interface IProductDesignService {

    List<ProductDesign> getProductDesigns();

    List<ProductDesign> getProductDesignsByCategory(String category);

}
