package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.DiamondPrice;
import com.fpt.jpos.pojo.enums.Clarity;
import com.fpt.jpos.pojo.enums.Color;
import com.fpt.jpos.pojo.enums.Cut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDiamondPriceRepository extends JpaRepository<DiamondPrice, Integer> {

    @Query(value = "Select * from DiamondPriceList where carat_weight = ?1 and clarity = ?2 and color = ?3 and cut = ?4", nativeQuery = true)
    List<DiamondPrice> findDiamondPriceByCaratWeightAndAndClarityAndColorAndCut(Double caratWeight, String clarity, String color, String cut);
}
