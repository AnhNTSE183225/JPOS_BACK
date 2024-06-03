package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.ProductDesign;
import com.fpt.jpos.service.ProductDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product-designs")
public class ProductDesignController {

    private final ProductDesignService productDesignService;

    @Autowired
    public ProductDesignController(ProductDesignService productDesignService) {
        this.productDesignService = productDesignService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDesign>> getAllProductDesigns() {
        List<ProductDesign> productDesigns = productDesignService.getProductDesigns();
        if (productDesigns.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDesigns);
    }

    @GetMapping("/{designType}")
    public ResponseEntity<List<ProductDesign>> getProductDesigns(@PathVariable String designType) {
        List<ProductDesign> productDesigns = productDesignService.getProductDesignsByCategory(designType);
        if (productDesigns.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDesigns);
    }
}
