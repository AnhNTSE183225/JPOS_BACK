package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Material;
import com.fpt.jpos.service.IMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MaterialController {

    private final IMaterialService materialService;

    @Autowired
    public MaterialController(IMaterialService materialService) {
        this.materialService = materialService;
    }

    @CrossOrigin
    @GetMapping("/material/all")
    public ResponseEntity<?> getAllMaterials() {
        List<Material> materialList = materialService.findAllMaterials();

        if(materialList == null || materialList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(materialList);
        }
    }
}
