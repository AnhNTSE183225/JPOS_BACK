package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Product;
import com.fpt.jpos.pojo.ProductDTO;
import com.fpt.jpos.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO productDTO) {
        try {
            System.out.println(productDTO.toString());
            Product product = productService.saveProduct(productDTO);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }
}
