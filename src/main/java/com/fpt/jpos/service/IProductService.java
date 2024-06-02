package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Product;

import java.util.List;

public interface IProductService {
    public Product saveProduct(Product product);
    //Test
    public List<Product> findAll();
}
