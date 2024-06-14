package com.fpt.jpos.controller;

import com.fpt.jpos.dto.Diamond4CDTO;
import com.fpt.jpos.dto.DiamondPriceProjection;
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
    public ResponseEntity<?> getAllDiamondPrice(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        List<DiamondPrice> diamondPriceListing = diamondPriceService.getDiamondPrices(pageNo, pageSize);
        if(diamondPriceListing.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(diamondPriceListing);
        }
    }

    @CrossOrigin
    @PostMapping("/get-price-by-4C")
    public ResponseEntity<?> getDiamondPriceBy4C(@RequestBody Diamond4CDTO diamond4CDTO) {

        double diamondPriceList = diamondPriceService.getDiamondPricesBy4C(diamond4CDTO);
        return ResponseEntity.ok(diamondPriceList); // thrown exception in service
    }

    @CrossOrigin
    @GetMapping("/prices")
    public ResponseEntity<?> getAllDiamondPrice() {
        List<DiamondPriceProjection> diamondPrices = diamondPriceService.getDiamondPrices();
        if(diamondPrices.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(diamondPrices);
        }
    }
}
