package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.DiamondPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IDiamondPriceRepository extends JpaRepository<DiamondPrice, Integer> {

    @Query(value = "SELECT * FROM [DiamondPriceList]", nativeQuery = true)
    List<DiamondPrice> getAllDiamondPrice();

    @Query(value = "SELECT TOP 1 [price] FROM [DiamondPriceList] WHERE [origin] = ?1 AND [shape] = ?2 AND [carat_weight_from] < ?3 AND [carat_weight_to] >= ?3 AND [color] = ?4 AND [clarity] = ?5 AND [cut] = ?6 AND [effective_date] <= GETDATE() ORDER BY [effective_date] DESC", nativeQuery = true)
    Double getSingleDiamondPrice(String origin, String shape, Double caratWeight, String color, String clarity, String cut);

    @Transactional
    @Modifying
    @Query(value = "UPDATE DiamondPriceList SET price = ?1 WHERE diamond_price_id = ?2", nativeQuery = true)
    int update(Double newPrice, Integer diamondPriceId);
}
