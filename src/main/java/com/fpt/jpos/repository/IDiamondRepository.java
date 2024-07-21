package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Diamond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiamondRepository extends JpaRepository<Diamond, Integer> {

    @Query(value = """
            select d.*, dp.price, dp.effective_date from Diamond d
            join
            (
            	select dp.*
            	from DiamondPriceList dp
            	join
            	(
            		select dp.origin, dp.shape, dp.carat_weight_from, dp.carat_weight_to, dp.color, dp.clarity, dp.cut, max(dp.effective_date) as max_effective_date
            		from DiamondPriceList dp
            		group by dp.origin, dp.shape, dp.carat_weight_from, dp.carat_weight_to, dp.color, dp.clarity, dp.cut
            	) as dp1
            	on
            	dp.origin = dp1.origin and
            	dp.shape = dp1.shape and
            	dp.carat_weight_from = dp1.carat_weight_from and
            	dp.carat_weight_to = dp1.carat_weight_to and
            	dp.color = dp1.color and
            	dp.clarity = dp1.clarity and
            	dp.cut = dp1.cut and
            	dp.effective_date = dp1.max_effective_date
            ) as dp
            on
            d.origin = dp.origin and
            d.shape = dp.shape and
            d.carat_weight > dp.carat_weight_from and
            d.carat_weight <= dp.carat_weight_to and
            d.color = dp.color and
            d.clarity = dp.clarity and
            d.cut = dp.cut
            where
            d.origin = ?1 AND
            d.shape IN ?2 AND
            dp.price >= ?3 AND
            dp.price <= ?4 AND
            d.carat_weight >= ?5 AND
            d.carat_weight <= ?6  AND
            d.color IN ?7 AND
            d.clarity IN ?8 AND
            d.cut IN ?9
            """, nativeQuery = true)
    List<Object[]> getDiamondBy4C(String origin, List<String> shapeList, Double minPrice, Double maxPrice, Double minCarat, Double maxCarat, List<String> colorList, List<String> clarityList, List<String> cutList);
}
