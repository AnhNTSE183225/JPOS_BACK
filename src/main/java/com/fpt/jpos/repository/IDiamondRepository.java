package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Diamond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiamondRepository extends JpaRepository<Diamond, Integer> {

    @Query(value = """
            SELECT d.*,dp1.price, m.max_effective_date 
            FROM 
            (
            	SELECT dp.origin, dp.shape, dp.clarity, dp.color, dp.cut, dp.carat_weight_from, dp.carat_weight_to, MAX(dp.effective_date) max_effective_date
                FROM DiamondPriceList AS dp 
                WHERE 
                dp.origin = ?1 AND 
                dp.shape IN ?2 AND
                dp.price >= ?3 AND
                dp.price <= ?4 AND
                dp.carat_weight_from >= ?5 AND 
                dp.carat_weight_to <= ?6  AND
                dp.color IN ?7 AND 
                dp.clarity IN ?8 AND 
                dp.cut IN ?9
                GROUP BY dp.origin, dp.shape, dp.clarity, dp.color, dp.cut, dp.carat_weight_from, dp.carat_weight_to
            )
            AS m 
            JOIN DiamondPriceList AS dp1
            ON  
            m.origin = dp1.origin AND 
            m.shape = dp1.shape AND 
            m.clarity = dp1.clarity AND 
            m.color = dp1.color AND 
            m.cut = dp1.cut AND 
            m.carat_weight_from = dp1.carat_weight_from AND 
            m.carat_weight_to = dp1.carat_weight_to AND 
            m.max_effective_date = dp1.effective_date 
            JOIN Diamond d 
            ON 
            d.origin = m.origin AND 
            d.shape = m.shape AND 
            d.clarity = m.clarity AND 
            d.color = m.color AND 
            d.cut = m.cut AND 
            d.carat_weight BETWEEN m.carat_weight_from AND m.carat_weight_to 
            """, nativeQuery = true)
    List<Object[]> getDiamondBy4C(String origin, List<String> shapeList, Double minPrice, Double maxPrice, Double minCarat, Double maxCarat, List<String> colorList, List<String> clarityList, List<String> cutList);
}
