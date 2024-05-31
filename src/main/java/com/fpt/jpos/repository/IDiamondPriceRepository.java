package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.DiamondPrice;
import com.fpt.jpos.pojo.enums.Clarity;
import com.fpt.jpos.pojo.enums.Color;
import com.fpt.jpos.pojo.enums.Cut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDiamondPriceRepository extends JpaRepository<DiamondPrice, Integer> {
    List<DiamondPrice> findDiamondPriceByCaratWeightAndAndClarityAndColorAndCut(Double caratWeight, Clarity clarity, Color color, Cut cut);
}
