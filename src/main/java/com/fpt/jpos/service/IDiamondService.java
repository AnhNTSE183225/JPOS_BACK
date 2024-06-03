package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Diamond;

import java.util.List;

public interface IDiamondService {
    List<Diamond> findDiamondsBy4C(Double fromCaratWeight, Double toCaratWeight, String clarity, String color, String cut, String shape);
    List<Diamond> getAllDiamonds();
}
