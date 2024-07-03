package com.fpt.jpos.controller;

import com.fpt.jpos.dto.DiamondQueryDTO;
import com.fpt.jpos.pojo.Diamond;
import com.fpt.jpos.service.IDiamondService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diamond")
@RequiredArgsConstructor
@CrossOrigin
public class DiamondController {

    private final IDiamondService diamondService;

    @GetMapping("/get-all")
    @PreAuthorize("hasAnyAuthority('customer','admin', 'staff')")
    public ResponseEntity<?> getAllDiamond(@RequestParam int pageNo, @RequestParam int pageSize) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondService.getAllDiamond(pageNo, pageSize));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @GetMapping("/get-by-id/{diamondId}")
    @PreAuthorize("hasAnyAuthority('customer','admin', 'staff')")
    public ResponseEntity<?> getDiamondById(@PathVariable int diamondId) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondService.getDiamondById(diamondId));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @PostMapping("/get-diamond-with-price-by-4C")
    @PreAuthorize("hasAnyAuthority('customer','admin', 'staff')")
    public ResponseEntity<?> getDiamondWithPriceBy4C(@RequestBody DiamondQueryDTO diamondQueryDTO, @RequestParam int pageNo, @RequestParam int pageSize) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondService.getDiamondWithPriceBy4C(diamondQueryDTO, pageNo, pageSize));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('customer','admin', 'staff')")
    public ResponseEntity<?> updateDiamond(@RequestBody Diamond diamond) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondService.updateDiamond(diamond));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @DeleteMapping("/delete/{diamondId}")
    @PreAuthorize("hasAnyAuthority('customer','admin', 'staff')")
    public ResponseEntity<?> deleteDiamond(@PathVariable int diamondId) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            diamondService.deleteDiamond(diamondId);
            response = ResponseEntity.ok().build();
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }
}
