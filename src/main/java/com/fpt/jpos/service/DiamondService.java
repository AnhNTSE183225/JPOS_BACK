package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Diamond;
import com.fpt.jpos.repository.IDiamondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiamondService {

    @Autowired
    private IDiamondRepository diamondRepository;

    public List<Diamond> getAllDiamonds() {
        return diamondRepository.findAll();
    }

//    public List<Diamond> getDiamondsByCategory(String category) {
//        return diamondRepository.findByCategory(category);
//    }
}
