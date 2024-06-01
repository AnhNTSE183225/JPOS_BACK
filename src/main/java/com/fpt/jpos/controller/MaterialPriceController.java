package com.fpt.jpos.controller;

import com.fpt.jpos.service.IMaterialPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MaterialPriceController {

    private final IMaterialPriceService materialPriceService;

    @Autowired
    public MaterialPriceController(IMaterialPriceService materialPriceService) {
        this.materialPriceService = materialPriceService;
    }


    @GetMapping("/materialPrices/{id}")
    public ResponseEntity<?> getLatestMaterialPriceById(@PathVariable Integer id) {

        Double latestPrice = materialPriceService.getLatestPriceById(id);

        return ResponseEntity.ok(latestPrice);
    }

}
