package com.fpt.jpos.service;

import com.fpt.jpos.pojo.ProductShellMaterial;

import java.util.List;

public interface IProductShellMaterialService {
    List<ProductShellMaterial> findByShellId(Integer shellId);

}
