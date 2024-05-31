package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Diamond4C;
import com.fpt.jpos.pojo.DiamondPrice;
import com.fpt.jpos.service.IDiamondPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if (diamondPriceList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(diamondPriceList);
        }
    }

    @CrossOrigin
    @GetMapping("/get-price-by-4C")
    public ResponseEntity<?> getDiamondPriceBy4C(@RequestBody Diamond4C diamond4C) {

        System.out.println(diamond4C.getCut());
        System.out.println(diamond4C.getCaratWeight());
        System.out.println(diamond4C.getColor());
        System.out.println(diamond4C.getClarity());

        Double mostRecentPrice = diamondPriceService.getDiamondPriceBy4C(diamond4C);
        if (mostRecentPrice == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No prices found.");
        } else {
            return ResponseEntity.ok(mostRecentPrice);
        }
    }
}
