package com.fpt.jpos.controller;

import com.fpt.jpos.config.RollbarConfig;
import com.fpt.jpos.pojo.Material;
import com.fpt.jpos.service.IDiamondPriceService;
import com.fpt.jpos.service.IMaterialPriceService;
import com.fpt.jpos.service.IMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/diamond-price/{origin}/{shape}/{caratFrom}-{caratTo}")
    public ResponseEntity<?> getDiamondPriceByOriginAndShape(@PathVariable String origin, @PathVariable String shape, @PathVariable Double caratFrom, @PathVariable Double caratTo) {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(diamondPriceService.getDiamondPriceByOriginAndShapeAndCaratRange(origin, shape, caratFrom, caratTo));
            new RollbarConfig().rollbar().debug("Here is some debug message");
        } catch (Exception ex) {
            response = ResponseEntity.status(400).build();
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
}
