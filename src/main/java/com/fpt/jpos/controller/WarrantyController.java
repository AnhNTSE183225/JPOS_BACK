package com.fpt.jpos.controller;

import com.fpt.jpos.service.IWarrantyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/warranty")
@RequiredArgsConstructor
public class WarrantyController {

    private final IWarrantyService warrantyService;

    @GetMapping("product/{productId}")
    public ResponseEntity<?> getWarrantyByProductId(@PathVariable Integer productId) {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(warrantyService.getWarrantyByProductId(productId));
        } catch (Exception ex) {
            ex.printStackTrace();
            response = ResponseEntity.status(404).build();
        }

        return response;
    }
}
