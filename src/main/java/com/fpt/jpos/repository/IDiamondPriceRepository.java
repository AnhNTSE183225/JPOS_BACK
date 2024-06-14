package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.DiamondPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiamondPriceRepository extends JpaRepository<DiamondPrice, Integer> {

    @Query(value = "Select * from [DiamondPriceList] where carat_weight = ?1 and clarity = ?2 and color = ?3 and cut = ?4 and shape =?5", nativeQuery = true)
    List<DiamondPrice> findDiamondBy4C(Double caratWeight, String clarity, String color, String cut, String shape);

//    @Query(value = "Select AVG([price]) from [DiamondPriceList] where carat_weight >= ?1 and carat_weight <= ?2 and clarity = ?3 and color = ?4 and cut = ?5 and shape =?6", nativeQuery = true)
//    Double findDiamondBy4CInRange(Double caratWeightFrom, Double caratWeightTo, String clarity, String color, String cut, String shape);

    @Query(value = "SELECT MAX([carat_weight]) FROM [DiamondPriceList]",nativeQuery = true)
    Double getMaximumCaratWeight();
}
