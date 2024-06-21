package com.fpt.jpos.controller;

import com.fpt.jpos.dto.DiamondQueryDTO;
import com.fpt.jpos.pojo.Diamond;
import com.fpt.jpos.service.IDiamondService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diamond")
@RequiredArgsConstructor
public class DiamondController {

    private final IDiamondService diamondService;

    @CrossOrigin
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDiamond(@RequestParam int pageNo, @RequestParam int pageSize) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondService.getAllDiamond(pageNo, pageSize));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @CrossOrigin
    @GetMapping("/get-by-id/{diamondId}")
    public ResponseEntity<?> getDiamondById(@PathVariable int diamondId) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondService.getDiamondById(diamondId));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @CrossOrigin
    @PostMapping("/get-diamond-with-price-by-4C")
    public ResponseEntity<?> getDiamondWithPriceBy4C(@RequestBody DiamondQueryDTO diamondQueryDTO, @RequestParam int pageNo, @RequestParam int pageSize) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondService.getDiamondWithPriceBy4C(diamondQueryDTO, pageNo, pageSize));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @CrossOrigin
    @PutMapping("/update")
    public ResponseEntity<?> updateDiamond(@RequestBody Diamond diamond) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(diamondService.updateDiamond(diamond));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @CrossOrigin
    @DeleteMapping("/delete/{diamondId}")
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
