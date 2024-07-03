package com.fpt.jpos.controller;

import com.fpt.jpos.dto.DiamondPriceQueryDTO;
import com.fpt.jpos.pojo.DiamondPrice;
import com.fpt.jpos.service.IDiamondPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diamond-price")
@RequiredArgsConstructor
public class DiamondPriceController {
    private final IDiamondPriceService diamondPriceService;

    @CrossOrigin
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDiamondPrice() {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondPriceService.getAllDiamondPrice());
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @CrossOrigin
    @PostMapping("/get-single-price")
    public ResponseEntity<?> getSingleDiamondPrice(@RequestBody DiamondPriceQueryDTO diamondPriceQueryDTO) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondPriceService.getSingleDiamondPrice(diamondPriceQueryDTO));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<?> addDiamondPrice(@RequestBody DiamondPrice diamondPrice) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondPriceService.addDiamondPrice(diamondPrice));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @CrossOrigin
    @PutMapping("/update")
    public ResponseEntity<?> updateDiamondPrice(@RequestBody DiamondPrice diamondPrice) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondPriceService.updateDiamondPrice(diamondPrice));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @CrossOrigin
    @GetMapping("/delete")
    public ResponseEntity<?> deleteDiamondPrice(@RequestParam int diamondPriceId) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            diamondPriceService.deletePrice(diamondPriceId);
            response = ResponseEntity.ok().build();
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }
}
