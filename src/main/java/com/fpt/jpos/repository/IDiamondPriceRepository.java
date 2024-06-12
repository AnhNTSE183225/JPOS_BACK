package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.DiamondPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiamondPriceRepository extends JpaRepository<DiamondPrice, Integer> {

    @Query(value = "Select * from [DiamondPriceList] where carat_weight_from >= ?1 and carat_weight_to <= ?1 and clarity = ?3 and color = ?4 and cut = ?5", nativeQuery = true)
    List<DiamondPrice> findDiamondPriceByCaratWeightAndAndClarityAndColorAndCut(Double caratWeight, String clarity, String color, String cut);
}
