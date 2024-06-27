package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Material;
import com.fpt.jpos.repository.IMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialService implements IMaterialService {

    private final IMaterialRepository materialRepository;

    @Autowired
    public MaterialService(IMaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public List<Material> findAllMaterials() {
        return materialRepository.findAll();
    }

    @Override
    public Material findMaterialById(int id) {
        Optional<Material> material = materialRepository.findById(id);
        return material.orElse(null);
    }

    @Override
    public void saveOrUpdateMaterial(Material material) {
        materialRepository.save(material);
    }

    @Override
    public void deleteMaterial(Material material) {
        materialRepository.delete(material);
    }

}
