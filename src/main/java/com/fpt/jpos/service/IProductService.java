package com.fpt.jpos.service;

import com.fpt.jpos.dto.ProductDTO;
import com.fpt.jpos.pojo.Product;

import java.util.List;

public interface IProductService {
    public Product saveProduct(ProductDTO productDTO);

    //Test
    public List<Product> findAll();
}
