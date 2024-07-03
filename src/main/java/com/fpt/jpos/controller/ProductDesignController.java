package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.ProductDesign;
import com.fpt.jpos.service.ProductDesignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-designs")
@CrossOrigin
@RequiredArgsConstructor
public class ProductDesignController {

    private final ProductDesignService productDesignService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDesign>> getAllProductDesigns() {
        List<ProductDesign> productDesigns = productDesignService.getProductDesigns();
        if (productDesigns.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productDesigns);
    }

    @GetMapping("/filter/{designType}")
    public ResponseEntity<List<ProductDesign>> getProductDesigns(@PathVariable String designType) {
        List<ProductDesign> productDesigns = productDesignService.getProductDesignsByCategory(designType);
        if (productDesigns.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDesigns);
    }

    @GetMapping("/{productDesignId}")
    public ResponseEntity<?> findById(@PathVariable Integer productDesignId) {
        try {
            return ResponseEntity.ok(productDesignService.findById(productDesignId));
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }
}
