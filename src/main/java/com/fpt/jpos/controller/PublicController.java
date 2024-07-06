package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Material;
import com.fpt.jpos.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    private final IDiamondPriceService diamondPriceService;
    private final IMaterialPriceService materialPriceService;
    private final IMaterialService materialService;
    private final IDiamondService diamondService;

    @GetMapping("/diamond-price/get-all")
    public ResponseEntity<?> getAllDiamondPrice() {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondPriceService.getAllDiamondPrice());
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @GetMapping("/material-price/find-all")
    public ResponseEntity<?> getAllMaterialPrices() {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(materialPriceService.findAll());
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @GetMapping("/material/all")
    public ResponseEntity<?> getAllMaterials() {
        List<Material> materialList = materialService.findAllMaterials();

        if (materialList == null || materialList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(materialList);
        }
    }

    @GetMapping("/material/{id}")
    public ResponseEntity<?> getLatestMaterialPriceById(@PathVariable Integer id) {
        try {
            Double latestPrice = materialPriceService.getLatestPriceById(id);
            return ResponseEntity.ok(latestPrice);
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDiamond() {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondService.getAllDiamond());
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }
}
