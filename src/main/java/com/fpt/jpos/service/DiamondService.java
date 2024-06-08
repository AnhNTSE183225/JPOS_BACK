package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Diamond;
import com.fpt.jpos.repository.IDiamondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiamondService implements IDiamondService{

    private final IDiamondRepository diamondRepository;

    @Autowired
    public DiamondService(IDiamondRepository diamondRepository) {
        this.diamondRepository = diamondRepository;
    }

    @Override
    public List<Diamond> getAllDiamonds() {
        return diamondRepository.findAll();
    }

    @Override
    public List<Diamond> findDiamondsBy4C(Double fromCaratWeight, Double toCaratWeight, String clarity, String color, String cut, String shape) {
        return diamondRepository.findDiamondsBy4C(fromCaratWeight, toCaratWeight, clarity, color, cut, shape);
    }

//    public List<Diamond> getDiamondsByCategory(String category) {
//        return diamondRepository.findByCategory(category);
//    }
}
