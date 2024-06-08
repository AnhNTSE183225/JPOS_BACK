package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.DiamondPrice;
import com.fpt.jpos.pojo.enums.Clarity;
import com.fpt.jpos.pojo.enums.Color;
import com.fpt.jpos.pojo.enums.Cut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiamondPriceRepository extends JpaRepository<DiamondPrice, Integer> {

    @Query(value = "Select * from [DiamondPriceList] where carat_weight >= ?1 and carat_weight <= ?2 and clarity = ?3 and color = ?4 and cut = ?5", nativeQuery = true)
    List<DiamondPrice> findDiamondPriceByCaratWeightAndAndClarityAndColorAndCut(Double fromCaratWeight, Double toCaratWeight, String clarity, String color, String cut);
}
