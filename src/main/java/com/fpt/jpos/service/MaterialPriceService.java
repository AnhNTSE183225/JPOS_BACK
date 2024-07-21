package com.fpt.jpos.service;

import com.fpt.jpos.pojo.MaterialPrice;
import com.fpt.jpos.repository.IMaterialPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    //Binh
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
    //Binh
    @Override
    public boolean addMaterialPrice(Integer materialId, Double materialPrice) {
        Date today = Calendar.getInstance().getTime();
        int count = materialPriceRepository.addMaterialPrice(materialId, today, materialPrice);
        return count > 0;
    }


    // 2024-06-28 16:45:31.080
    // format ngày lấy giống trong db
    //Binh
    @Override
    public boolean updateMaterialPrice(Double materialPrice, Integer materialId, String effectiveDateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date effectiveDate = dateFormat.parse(effectiveDateStr);
            int count = materialPriceRepository.updateMaterialPrice(materialPrice, materialId, effectiveDate);
            return count > 0;
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<MaterialPrice> findAll() {
        return materialPriceRepository.findAll();
    }
}
