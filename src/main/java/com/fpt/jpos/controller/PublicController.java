package com.fpt.jpos.controller;

import com.fpt.jpos.dto.DiamondPriceQueryDTO;
import com.fpt.jpos.dto.DiamondQueryDTO;
import com.fpt.jpos.pojo.Material;

import com.fpt.jpos.pojo.ProductDesign;
import com.fpt.jpos.pojo.enums.*;

import com.fpt.jpos.pojo.enums.DesignType;
import com.fpt.jpos.pojo.enums.OrderStatus;
import com.fpt.jpos.pojo.enums.Shape;
import com.fpt.jpos.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final IProductDesignService productDesignService;
    private final IProductShellMaterialService productShellMaterialService;
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
    @GetMapping("/enum/designTypes")
    public ResponseEntity<?> getDesignType() {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(DesignType.ring.getDeclaringClass().getEnumConstants());
        } catch (Exception ex) {
            response = ResponseEntity.status(400).build();
        }

        return response;
    }

    @GetMapping("/enum/orderStatus")
    public ResponseEntity<?> getOrderStatus() {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(OrderStatus.wait_sale_staff.getDeclaringClass().getEnumConstants());
        } catch (Exception ex) {
            response = ResponseEntity.status(400).build();
        }

        return response;
    }

    @GetMapping("/product-designs/all")
    public ResponseEntity<List<ProductDesign>> getAllProductDesigns() {
        List<ProductDesign> productDesigns = productDesignService.getProductDesigns();
        if (productDesigns.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productDesigns);
    }

    @GetMapping("/product-designs/get-configurations")
    public ResponseEntity<?> getConfigurations(@RequestParam String designType) {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(productDesignService.findByDesignType(designType));
        } catch (Exception ex) {
            response = ResponseEntity.status(400).build();
            ex.printStackTrace();
        }

        return response;
    }

    @GetMapping("/product-designs/{productDesignId}")
    public ResponseEntity<?> findById(@PathVariable Integer productDesignId) {
        try {
            return ResponseEntity.ok(productDesignService.findById(productDesignId));
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @CrossOrigin
    @GetMapping("/product-shell-material/{shellId}")
    public ResponseEntity<?> getProductShellMaterial(@PathVariable int shellId) {
        return ResponseEntity.ok(productShellMaterialService.findByShellId(shellId));
    }

    @PostMapping("/diamond/get-diamond-with-price-by-4C")
    public ResponseEntity<?> getDiamondWithPriceBy4C(@RequestBody DiamondQueryDTO diamondQueryDTO) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondService.getDiamondWithPriceBy4C(diamondQueryDTO));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @GetMapping("/diamond/get-by-id/{diamondId}")
    public ResponseEntity<?> getDiamondById(@PathVariable int diamondId) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondService.getDiamondById(diamondId));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @PostMapping("/diamond-price/get-single-price")
    public ResponseEntity<?> getSingleDiamondPrice(@RequestBody DiamondPriceQueryDTO diamondPriceQueryDTO) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondPriceService.getSingleDiamondPrice(diamondPriceQueryDTO));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }
}
