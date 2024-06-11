package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.ProductShellMaterial;
import com.fpt.jpos.service.ProductShellMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductShellMaterialController {

    private final ProductShellMaterialService productShellMaterialService;

    @Autowired
    public ProductShellMaterialController(ProductShellMaterialService productShellMaterialService) {
        this.productShellMaterialService = productShellMaterialService;
    }

    @CrossOrigin
    @GetMapping("/product-shell-material/{shellId}")
    public ResponseEntity<?> getProductShellMaterial(@PathVariable int shellId) {
        return ResponseEntity.ok(productShellMaterialService.findByShellId(shellId));
    }

}
