package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Material;
import com.fpt.jpos.repository.IMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService implements IMaterialService{

    private final IMaterialRepository materialRepository;

    @Autowired
    public MaterialService(IMaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public List<Material> findAllMaterials() {
        return materialRepository.findAll();
    }
}
