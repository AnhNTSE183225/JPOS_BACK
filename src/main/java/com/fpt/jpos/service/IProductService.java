package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Product;
import com.fpt.jpos.pojo.ProductDTO;

import java.util.List;

public interface IProductService {
    public Product saveProduct(ProductDTO productDTO);
    //Test
    public List<Product> findAll();
}
