package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.DiamondPrice;
import com.fpt.jpos.service.IDiamondPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DiamondPriceController {
    private IDiamondPriceService diamondPriceService;

    @Autowired
    public DiamondPriceController(IDiamondPriceService diamondPriceService) {
        this.diamondPriceService = diamondPriceService;
    }

    @CrossOrigin
    @GetMapping("/diamond-prices")
    public ResponseEntity<List<DiamondPrice>> getAllDiamondPrice() {
        List<DiamondPrice> diamondPriceList = diamondPriceService.getDiamondPrices();
        if(diamondPriceList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(diamondPriceList);
        }
    }
}
