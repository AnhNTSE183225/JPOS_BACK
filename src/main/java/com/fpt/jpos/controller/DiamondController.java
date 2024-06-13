package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Diamond;
import com.fpt.jpos.repository.IDiamondRepository;
import com.fpt.jpos.service.DiamondService;

import com.fpt.jpos.service.IDiamondService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DiamondController {


    private final IDiamondService diamondService;

    @Autowired
    public DiamondController(IDiamondService diamondService) {
        this.diamondService = diamondService;
    }

    @CrossOrigin
    @GetMapping("/get-diamonds-by-4C")
    public ResponseEntity<?> findDiamondBy4C(@RequestParam Double fromCaratWeight,
                                             @RequestParam Double toCaratWeight,
                                             @RequestParam String clarity,
                                             @RequestParam String color,
                                             @RequestParam String cut,
                                             @RequestParam String shape) {

        List<Diamond> diamondList = diamondService.findDiamondsBy4C(fromCaratWeight, toCaratWeight, clarity, color, cut, shape);

        if (diamondList == null || diamondList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(diamondList);
        }
    }

    @CrossOrigin
    @GetMapping("/diamonds")
    public ResponseEntity<?> getAllDiamonds() {
        try {
            return ResponseEntity.ok(diamondService.getAllDiamonds());
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @CrossOrigin
    @GetMapping("/diamonds/{diamondId}")
    public ResponseEntity<?> getDiamondById(@PathVariable Integer diamondId) {
        try {
            return ResponseEntity.ok(diamondService.findById(diamondId));
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }
}
