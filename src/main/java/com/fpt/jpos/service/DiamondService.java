package com.fpt.jpos.service;

import com.fpt.jpos.dto.DiamondQuery;
import com.fpt.jpos.pojo.Diamond;
import com.fpt.jpos.repository.IDiamondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiamondService implements IDiamondService {

    private final IDiamondRepository diamondRepository;

    @Autowired
    public DiamondService(IDiamondRepository diamondRepository) {
        this.diamondRepository = diamondRepository;
    }

    @Override
    public List<Diamond> getAllDiamonds(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Diamond> page = diamondRepository.findAll(pageable);
        return page.getContent();
    }

    @Override
    public Diamond findById(Integer diamondId) {
        return diamondRepository.findById(diamondId).orElseThrow();
    }

    @Override
    public List<Diamond> diamondQuery(DiamondQuery diamondQuery, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Diamond> page = diamondRepository.diamondQuery(diamondQuery.getOrigin(),diamondQuery.getCaratWeight(),diamondQuery.getColorList(),diamondQuery.getClarityList(),diamondQuery.getCutList(),diamondQuery.getShapeList(),diamondQuery.getPrice(),pageable);
        return page.getContent();
    }

    @Override
    public List<Diamond> getDiamondsById(List<Integer> diamondIds) {
        return diamondRepository.getDiamondsByIds(diamondIds);
    }

    @Override
    public List<Diamond> findDiamondsBy4C(Double fromCaratWeight, Double toCaratWeight, String clarity, String color, String cut, String shape) {
        return diamondRepository.findDiamondsBy4C(fromCaratWeight, toCaratWeight, clarity, color, cut, shape);
    }

}
