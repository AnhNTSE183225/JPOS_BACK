package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Material;
import com.fpt.jpos.service.IMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material")
@CrossOrigin
public class MaterialController {

    private final IMaterialService materialService;

    @Autowired
    public MaterialController(IMaterialService materialService) {
        this.materialService = materialService;
    }

    @PreAuthorize("hasAnyAuthority('customer','admin', 'staff')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllMaterials() {
        List<Material> materialList = materialService.findAllMaterials();

        if (materialList == null || materialList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(materialList);
        }
    }

    //Binh
    @PreAuthorize("hasAuthority('staff') or hasAuthority('admin')")
    @PostMapping("/add")
    public ResponseEntity<?> addMaterial(@RequestBody Material material) {
        if (material == null) {
            return ResponseEntity.noContent().build();
        }
        material.setMaterialId(0);
        materialService.saveOrUpdateMaterial(material);
        return ResponseEntity.ok(material);

    }

    //Binh
    @PreAuthorize("hasAuthority('staff') or hasAuthority('admin')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaterial(@PathVariable Integer id, @RequestBody Material material) {
        if (id == null || id <= 0) {
            return ResponseEntity.noContent().build();
        }
        material.setMaterialId(id);
        materialService.saveOrUpdateMaterial(material);
        return ResponseEntity.ok(material);
    }

    //Binh
    @PreAuthorize("hasAuthority('staff') or hasAuthority('admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaterial(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.noContent().build();
        }
        materialService.deleteMaterial(materialService.findMaterialById(id));
        return ResponseEntity.ok(materialService.findMaterialById(id));
    }
}
