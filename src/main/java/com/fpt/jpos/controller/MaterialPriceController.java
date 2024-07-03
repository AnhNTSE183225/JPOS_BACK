package com.fpt.jpos.controller;

import com.fpt.jpos.dto.MaterialPriceDTO;
import com.fpt.jpos.service.IMaterialPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/materialPrices")
@RequiredArgsConstructor
@CrossOrigin
public class MaterialPriceController {

    private final IMaterialPriceService materialPriceService;

    @GetMapping("/find-all")
    public ResponseEntity<?> getAllMaterialPrices() {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(materialPriceService.findAll());
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLatestMaterialPriceById(@PathVariable Integer id) {
        try {
            Double latestPrice = materialPriceService.getLatestPriceById(id);
            return ResponseEntity.ok(latestPrice);
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }

    // return true thôi chứ cũng không biết return cái gì nữa
    @PostMapping("/add")
    public ResponseEntity<?> addMaterialPrice(@RequestBody MaterialPriceDTO.MaterialPriceCreateResponse materialPriceCreateResponse) {
        if (materialPriceCreateResponse.materialId == null || materialPriceCreateResponse.materialPrice == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(materialPriceService.addMaterialPrice(materialPriceCreateResponse.materialId, materialPriceCreateResponse.materialPrice));
    }

    // 2024-06-28 16:45:31.080
    // format ngày lấy giống trong db
    @PutMapping("/update")
    public ResponseEntity<?> updateMaterialPrice(@RequestBody MaterialPriceDTO.MaterialPriceUpdateResponse materialPriceUpdateResponse) {
        if (materialPriceUpdateResponse.materialId == null || materialPriceUpdateResponse.materialPrice == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(materialPriceService.updateMaterialPrice(materialPriceUpdateResponse.materialPrice, materialPriceUpdateResponse.materialId, materialPriceUpdateResponse.effectiveDate));
    }
}
