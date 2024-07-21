package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Material;
import com.fpt.jpos.pojo.MaterialPrice;
import com.fpt.jpos.pojo.MaterialPriceId;
import com.fpt.jpos.repository.IMaterialPriceRepository;
import com.fpt.jpos.repository.IMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialService implements IMaterialService {

    private final IMaterialRepository materialRepository;
    private final IMaterialPriceRepository materialPriceRepository;

    @Override
    public List<Material> findAllMaterials() {
        return materialRepository.findAll();
    }

    //Binh
    @Override
    public Material findMaterialById(int id) {
        Optional<Material> material = materialRepository.findById(id);
        return material.orElse(null);
    }

    @Override
    public void saveOrUpdateMaterial(Material material) {
        Material savedMaterial = materialRepository.save(material);
        if(materialPriceRepository.findMaterialPriceByMaterialId(savedMaterial.getMaterialId()).isEmpty()) {
            MaterialPrice materialPrice = MaterialPrice.builder()
                    .price(0.0)
                    .materialPriceId(MaterialPriceId.builder().materialId(savedMaterial.getMaterialId()).effectiveDate(new Date()).build())
                    .material(savedMaterial)
                    .build();
            materialPriceRepository.save(materialPrice);
        }
    }

    @Override
    public void deleteMaterial(Material material) {
        materialRepository.delete(material);
    }

}
