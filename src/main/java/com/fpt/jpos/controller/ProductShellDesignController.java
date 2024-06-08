package com.fpt.jpos.controller;

import com.fpt.jpos.service.IProductShellDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductShellDesignController {

    private final IProductShellDesignService productShellDesignService;

    @Autowired
    public ProductShellDesignController(IProductShellDesignService productShellDesignService) {
        this.productShellDesignService = productShellDesignService;
    }

    @CrossOrigin
    @GetMapping("/shells/{productDesignId}")
    public ResponseEntity<?> getByProductDesignId(@PathVariable Integer productDesignId) {
        try {
            return ResponseEntity.ok(productShellDesignService.getByProductDesignId(productDesignId));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
