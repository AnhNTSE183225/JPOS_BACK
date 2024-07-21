package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Material;
import com.fpt.jpos.pojo.enums.*;
import com.fpt.jpos.service.IDiamondPriceService;
import com.fpt.jpos.service.IMaterialPriceService;
import com.fpt.jpos.service.IMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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

    @GetMapping("/shape/get-shape")
    public ResponseEntity<?> getShapes() {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(Shape.round.getDeclaringClass().getEnumConstants());
        } catch (Exception ex) {
            ex.printStackTrace();
            response = ResponseEntity.status(400).build();
        }

        return response;
    }

    @GetMapping("/cut/get-cut")
    public ResponseEntity<?> getCut() {
        ResponseEntity<?> response;
        try {
            response = ResponseEntity.ok(Cut.Poor.getDeclaringClass().getEnumConstants());

        } catch (Exception ex) {
            ex.printStackTrace();
            response = ResponseEntity.status(400).build();
        }
        return response;
    }

    @GetMapping("/origin/get-origin")
    public ResponseEntity<?> getOrigins() {
        ResponseEntity<?> response;
        try {
            response = ResponseEntity.ok(Origin.NATURAL.getDeclaringClass().getEnumConstants());
        } catch (Exception ex)  {
            ex.printStackTrace();
            response = ResponseEntity.status(400).build();
        }
        return response;
    }

    @GetMapping("/clarity/get-clarity")
    public ResponseEntity<?> getClarity() {
        ResponseEntity<?> response;
        try {
            response = ResponseEntity.ok(Clarity.FL.getDeclaringClass().getEnumConstants());
        } catch (Exception ex) {
            ex.printStackTrace();
            response = ResponseEntity.status(400).build();
        }
        return response;
    }

    @GetMapping("/color/get-color")
    public ResponseEntity<?> getColor() {
        ResponseEntity<?> response;
        try {
            response = ResponseEntity.ok(Color.F.getDeclaringClass().getEnumConstants());
        } catch (Exception ex) {
            ex.printStackTrace();
            response = ResponseEntity.status(400).build();
        }
        return response;
    }

    @GetMapping("/fluorescence/get-fluorescence")
    public ResponseEntity<?> getFluorescence() {
        ResponseEntity<?> response;
        try {
            response = ResponseEntity.ok(Fluorescence.None.getDeclaringClass().getEnumConstants());
        } catch (Exception ex) {
            ex.printStackTrace();
            response = ResponseEntity.status(400).build();
        }
        return response;
    }

    @GetMapping("/polish/get-polish")
    public ResponseEntity<?> getPolish() {
        ResponseEntity<?> response;
        try {
            response = ResponseEntity.ok(Polish.Fair.getDeclaringClass().getEnumConstants());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            response = ResponseEntity.status(400).build();
        }
        return response;
    }

    @GetMapping("/symmetry/get-symmetry")
    public ResponseEntity<?> getSymmetry() {
        ResponseEntity<?> response;
        try {
            response = ResponseEntity.ok(Symmetry.Fair.getDeclaringClass().getEnumConstants());
        } catch (Exception ex) {
            ex.printStackTrace();
            response = ResponseEntity.status(400).build();
        }
        return response;
    }
}
