package com.fpt.jpos.repository;

import com.fpt.jpos.dto.DiamondPriceProjection;
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

    @Query(value = "SELECT * FROM [DiamondPriceList] ORDER BY [effective_date] DESC",nativeQuery = true)
    Page<DiamondPrice> findAllOrderByEffectiveDateDesc(Pageable pageable);

    @Query(value = "SELECT MAX([carat_weight]) FROM [DiamondPriceList]",nativeQuery = true)
    Double getMaximumCaratWeight();

    @Query(value = "SELECT \n" +
            "    CASE \n" +
            "        WHEN carat_weight BETWEEN 0 AND 0.4 THEN '0-0.4'\n" +
            "        WHEN carat_weight BETWEEN 0.4 AND 0.8 THEN '0.4-0.8'\n" +
            "        WHEN carat_weight BETWEEN 0.8 AND 1.2 THEN '0.8-1.2'\n" +
            "        WHEN carat_weight BETWEEN 1.2 AND 1.6 THEN '1.2-1.6'\n" +
            "        ELSE '1.6+'\n" +
            "    END as carat_weight_group,\n" +
            "    AVG(price) as average_price,\n" +
            "\torigin,\n" +
            "\tcolor,\n" +
            "\tclarity,\n" +
            "\tcut,\n" +
            "\teffective_date\n" +
            "FROM \n" +
            "    DiamondPriceList\n" +
            "GROUP BY \n" +
            "    CASE \n" +
            "        WHEN carat_weight BETWEEN 0 AND 0.4 THEN '0-0.4'\n" +
            "        WHEN carat_weight BETWEEN 0.4 AND 0.8 THEN '0.4-0.8'\n" +
            "        WHEN carat_weight BETWEEN 0.8 AND 1.2 THEN '0.8-1.2'\n" +
            "        WHEN carat_weight BETWEEN 1.2 AND 1.6 THEN '1.2-1.6'\n" +
            "        ELSE '1.6+'\n" +
            "    END, origin, color, clarity, cut, effective_date\n" +
            "order by effective_date,carat_weight_group, color, clarity, cut, average_price DESC\n",nativeQuery = true)
    List<DiamondPriceProjection> getDiamondPrices();

}
