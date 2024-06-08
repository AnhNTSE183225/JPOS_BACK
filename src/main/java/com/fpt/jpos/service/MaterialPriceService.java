package com.fpt.jpos.service;

import com.fpt.jpos.pojo.MaterialPrice;
import com.fpt.jpos.repository.IMaterialPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MaterialPriceService implements IMaterialPriceService {

    private final IMaterialPriceRepository materialPriceRepository;

    @Autowired
    public MaterialPriceService(IMaterialPriceRepository materialPriceRepository) {
        this.materialPriceRepository = materialPriceRepository;
    }


    @Override
    public Double getLatestPriceById(Integer id) {

        List<MaterialPrice> materialPriceList = materialPriceRepository.findMaterialPriceByMaterialId(id);

        Date today = Calendar.getInstance().getTime();
        Double result = 0.0;
        materialPriceList.sort((o1, o2) -> o2.getMaterialPriceId().getEffectiveDate().compareTo(o1.getMaterialPriceId().getEffectiveDate()));

        for (MaterialPrice materialPrice : materialPriceList) {
            if (materialPrice.getMaterialPriceId().getEffectiveDate().compareTo(today) <= 0) {
                result = materialPrice.getPrice();
                break;
            }
        }
        if (materialPriceList.isEmpty() || result == 0.0) {
            throw new RuntimeException("Material Price not found");
        }

        return result;
    }
}
