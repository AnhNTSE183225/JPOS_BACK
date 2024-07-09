package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Material;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IMaterialService {
    List<Material> findAllMaterials();

    Material findMaterialById(int id);

    @Transactional
    void saveOrUpdateMaterial(Material material);

    @Transactional
    void deleteMaterial(Material material);
}
