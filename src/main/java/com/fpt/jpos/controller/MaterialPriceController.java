package com.fpt.jpos.controller;

import com.fpt.jpos.service.IMaterialPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MaterialPriceController {

    private final IMaterialPriceService materialPriceService;

    @Autowired
    public MaterialPriceController(IMaterialPriceService materialPriceService) {
        this.materialPriceService = materialPriceService;
    }


    @CrossOrigin
    @GetMapping("/materialPrices/{id}")
    public ResponseEntity<?> getLatestMaterialPriceById(@PathVariable Integer id) {

        try {
            Double latestPrice = materialPriceService.getLatestPriceById(id);
            return ResponseEntity.ok(latestPrice);
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }

}
