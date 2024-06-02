package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Product;
import com.fpt.jpos.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    private IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @CrossOrigin
    @Transactional
    @PostMapping("/product/save")
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    //Test
    @CrossOrigin
    @GetMapping("/product/all")
    public ResponseEntity<?> getAllProduct() {
        return ResponseEntity.ok(productService.findAll());
    }
}
