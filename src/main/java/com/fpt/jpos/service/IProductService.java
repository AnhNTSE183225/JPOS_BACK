package com.fpt.jpos.service;

import com.fpt.jpos.dto.ProductDTO;
import com.fpt.jpos.pojo.Product;

import java.util.List;

public interface IProductService {
    Product saveProduct(ProductDTO productDTO);

    //Test
    List<Product> findAll();
}
