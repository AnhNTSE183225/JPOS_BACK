package com.fpt.jpos.controller;

import com.fpt.jpos.dto.DiamondQuery;
import com.fpt.jpos.pojo.Diamond;

import com.fpt.jpos.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> getAllDiamonds(@RequestParam int pageNo, @RequestParam int pageSize) {
        try {
            return ResponseEntity.ok(diamondService.getAllDiamonds(pageNo, pageSize));
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }

    @CrossOrigin
    @PostMapping("/diamonds/query")
    public ResponseEntity<?> diamondQuery(@RequestBody DiamondQuery diamondQuery, @RequestParam int pageNo, @RequestParam int pageSize) {
        List<Diamond> diamonds = diamondService.diamondQuery(diamondQuery, pageNo, pageSize);
        if (diamonds == null || diamonds.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(diamonds);
        }
    }

    @CrossOrigin
    @PostMapping("/get-multiple-diamonds-by-id")
    public ResponseEntity<?> getMultipleDiamondsById(@RequestBody List<Integer> diamondIds) {
        List<Diamond> diamondList = diamondService.getDiamondsById(diamondIds);
        if(diamondList == null || diamondList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(diamondList);
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
