package com.fpt.jpos.controller;

import com.fpt.jpos.service.IProductShellDesignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class ProductShellDesignController {

    private final IProductShellDesignService productShellDesignService;


    @GetMapping("/shells/{productDesignId}")
    @PreAuthorize("hasAuthority('customer') or hasAuthority('staff') or hasAuthority('admin')")
    public ResponseEntity<?> getByProductDesignId(@PathVariable Integer productDesignId) {
        try {
            return ResponseEntity.ok(productShellDesignService.getByProductDesignId(productDesignId));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
