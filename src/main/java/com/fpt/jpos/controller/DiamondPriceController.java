package com.fpt.jpos.controller;

import com.fpt.jpos.dto.Diamond4CDTO;
import com.fpt.jpos.pojo.DiamondPrice;
import com.fpt.jpos.service.IDiamondPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DiamondPriceController {
    private final IDiamondPriceService diamondPriceService;

    @Autowired
    public DiamondPriceController(IDiamondPriceService diamondPriceService) {
        this.diamondPriceService = diamondPriceService;
    }

    @CrossOrigin
    @GetMapping("/diamond-prices")
    public ResponseEntity<List<DiamondPrice>> getAllDiamondPrice() {
        List<DiamondPrice> diamondPriceList = diamondPriceService.getDiamondPrices();
        if (diamondPriceList == null || diamondPriceList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(diamondPriceList);
        }
    }

    @CrossOrigin
    @PostMapping("/get-price-by-4C")
    public ResponseEntity<?> getDiamondPriceBy4C(@RequestBody Diamond4CDTO diamond4CDTO) {

        double diamondPriceList = diamondPriceService.getDiamondPricesBy4C(diamond4CDTO);
        return ResponseEntity.ok(diamondPriceList); // thrown exception in service
    }
}
