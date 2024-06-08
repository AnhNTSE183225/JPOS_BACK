package com.fpt.jpos.service;

import com.fpt.jpos.pojo.ProductShellDesign;

import java.util.List;

public interface IProductShellDesignService {
    public List<ProductShellDesign> getByProductDesignId(Integer productDesignId);
}
