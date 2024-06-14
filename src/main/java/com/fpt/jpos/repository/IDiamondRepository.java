package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Diamond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiamondRepository extends JpaRepository<Diamond, Integer> {
    @Query(value = "Select * from [Diamond] where carat_weight >= ?1 and carat_weight <= ?2 and clarity = ?3 and color = ?4 and cut = ?5 and shape = ?6 and [active] = 1", nativeQuery = true)
    List<Diamond> findDiamondsBy4C(Double fromCaratWeight, Double toCaratWeight, String clarity, String color, String cut, String shape);

    @Query(value = "SELECT * FROM [Diamond] WHERE [diamond_id] IN (?1) and [active]=1",nativeQuery = true)
    List<Diamond> getDiamondsByIds(List<Integer> ids);

    @Query(value="SELECT d.[diamond_id], d.[diamond_code], d.[diamond_name],\n" +
            "d.[shape], d.[origin], d.[proportions],\n" +
            "d.[fluorescence], d.[symmetry], d.[polish],\n" +
            "d.[cut], d.[color], d.[clarity],\n" +
            "d.[carat_weight], d.[note], d.[image],\n" +
            "d.[active], p.[price]\n" +
            "FROM [Diamond] d\n" +
            "JOIN (\n" +
            "    SELECT shape, origin, cut, clarity, color, carat_weight, MAX(effective_date) AS latest_date\n" +
            "    FROM [DiamondPriceList]\n" +
            "    GROUP BY shape, origin, cut, clarity, color, carat_weight\n" +
            ") AS LD ON d.[shape] = LD.[shape] AND d.[origin] = LD.[origin] AND d.[cut] = LD.[cut] AND d.[clarity] = LD.[clarity] AND d.[color] = LD.[color] AND d.[carat_weight] = LD.[carat_weight]\n" +
            "JOIN [DiamondPriceList] p ON LD.[shape] = p.[shape] AND LD.[origin] = p.[origin] AND LD.[cut] = p.[cut] AND LD.[clarity] = p.[clarity] AND LD.[color] = p.[color] AND LD.[carat_weight] = p.[carat_weight] \n" +
            "AND LD.latest_date = p.[effective_date]\n" +
            "WHERE d.[origin] = (?1) and d.[carat_weight] >= (?2) and d.[color] in (?3) and d.[clarity] in (?4) and d.[cut] in (?5) and d.[shape] in (?6) and p.[price] >= (?7) and [active] = 1\n",nativeQuery = true)
    Page<Diamond> diamondQuery(String origin, Double fromCaratWeight, List<String> color, List<String> clarity, List<String> cut, List<String> shape, Double price ,Pageable pageable);
}
