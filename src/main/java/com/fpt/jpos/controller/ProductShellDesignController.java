package com.fpt.jpos.controller;

import com.fpt.jpos.service.IProductShellDesignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shells")
@RequiredArgsConstructor
@CrossOrigin
public class ProductShellDesignController {

    private final IProductShellDesignService productShellDesignService;


    @GetMapping("/{productDesignId}")
    @PreAuthorize("hasAuthority('customer') or hasAuthority('staff') or hasAuthority('admin')")
    public ResponseEntity<?> getByProductDesignId(@PathVariable Integer productDesignId) {
        try {
            return ResponseEntity.ok(productShellDesignService.getByProductDesignId(productDesignId));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{productShellDesignId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> deleteShell(@PathVariable Integer productShellDesignId) {
        ResponseEntity<?> response;

        try {
            this.productShellDesignService.deleteShell(productShellDesignId);
            response = ResponseEntity.ok().build();
        } catch (Exception ex) {
            response = ResponseEntity.status(400).build();
        }

        return response;
    }
}
