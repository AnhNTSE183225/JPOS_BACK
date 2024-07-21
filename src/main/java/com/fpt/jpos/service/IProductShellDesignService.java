package com.fpt.jpos.service;

import com.fpt.jpos.pojo.ProductShellDesign;

import java.util.List;

public interface IProductShellDesignService {
    List<ProductShellDesign> getByProductDesignId(Integer productDesignId);

    void deleteShell(Integer productShellDesignId);
}
