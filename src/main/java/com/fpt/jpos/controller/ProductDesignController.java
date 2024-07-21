package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.ProductDesign;
import com.fpt.jpos.service.IProductDesignService;
import com.fpt.jpos.service.ProductDesignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-designs")
@CrossOrigin
@RequiredArgsConstructor
public class ProductDesignController {

    private final IProductDesignService productDesignService;

    //Binh
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('customer','admin', 'staff')")
    public ResponseEntity<List<ProductDesign>> getAllProductDesigns() {
        List<ProductDesign> productDesigns = productDesignService.getProductDesigns();
        if (productDesigns.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productDesigns);
    }

    //Binh
    @GetMapping("/filter/{designType}")
    @PreAuthorize("hasAnyAuthority('customer','admin','staff')")
    public ResponseEntity<List<ProductDesign>> getProductDesigns(@PathVariable String designType) {
        List<ProductDesign> productDesigns = productDesignService.getProductDesignsByCategory(designType);
        if (productDesigns.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDesigns);
    }

    @GetMapping("/{productDesignId}")
    @PreAuthorize("hasAnyAuthority('customer','admin', 'staff')")
    public ResponseEntity<?> findById(@PathVariable Integer productDesignId) {
        try {
            return ResponseEntity.ok(productDesignService.findById(productDesignId));
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('admin','staff')")
    public ResponseEntity<?> update(@RequestBody ProductDesign productDesign) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(productDesignService.update(productDesign));
        } catch (Exception ex) {
            response = ResponseEntity.status(400).build();
            ex.printStackTrace();
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('admin','staff')")
    public ResponseEntity<?> add(@RequestBody ProductDesign productDesign) {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(productDesignService.add(productDesign));
        } catch (Exception ex) {
            response = ResponseEntity.status(400).build();
            ex.printStackTrace();
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @DeleteMapping("/{designId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> delete(@PathVariable Integer designId) {
        ResponseEntity<?> response;

        try {
            this.productDesignService.delete(designId);
            response = ResponseEntity.ok().build();
        } catch (Exception ex) {
            response = ResponseEntity.status(400).build();
            ex.printStackTrace();
        }

        return response;
    }

    @GetMapping("/get-configurations")
    @PreAuthorize("hasAnyAuthority('customer','admin','staff')")
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
}
