package com.fpt.jpos.service;

import com.fpt.jpos.dto.DiamondQuery;
import com.fpt.jpos.pojo.Diamond;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDiamondService {
    List<Diamond> findDiamondsBy4C(Double fromCaratWeight, Double toCaratWeight, String clarity, String color, String cut, String shape);

    List<Diamond> getAllDiamonds(int pageNo, int pageSize);

    Diamond findById(Integer diamondId);

    List<Diamond> diamondQuery(DiamondQuery diamondQuery, int pageNo, int pageSize);

    List<Diamond> getDiamondsById(List<Integer> diamondIds);
}
