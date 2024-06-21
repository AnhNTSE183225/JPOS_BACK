package com.fpt.jpos.repository;

import com.fpt.jpos.dto.DiamondQueryResponseDTO;
import com.fpt.jpos.pojo.Diamond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiamondRepository extends JpaRepository<Diamond, Integer> {

    @Query(value = """
            select d.*, dp.price, dp.latest_date from Diamond d
            left join
            (
            	select p.origin, p.shape, p.carat_weight_from, p.carat_weight_to, p.color, p.clarity, p.cut, p.price, pp.latest_date from DiamondPriceList p
            	inner join
            	(
            		select origin, shape, carat_weight_from, carat_weight_to, color, clarity, cut, MAX(effective_date) as latest_date from DiamondPriceList
            		where effective_date <= GETDATE()
            		group by origin, shape, carat_weight_from, carat_weight_to, color, clarity, cut
            	) pp
            	on p.effective_date = pp.latest_date and p.origin = pp.origin and p.shape = pp.shape and p.carat_weight_from = pp.carat_weight_from and p.carat_weight_to = pp.carat_weight_to and p.color = pp.color and p.cut = pp.cut and p.clarity = pp.clarity
            ) dp
            on d.origin = dp.origin and d.shape = dp.shape and d.carat_weight > dp.carat_weight_from and d.carat_weight <= dp.carat_weight_to and d.color = dp.color and d.clarity = dp.clarity and d.cut = dp.cut
            where d.origin = ?1 and d.shape in ?2 and dp.price >= ?3 and dp.price <= ?4 and d.carat_weight >= ?5 and d.carat_weight <= ?6 and d.color in ?7 and d.clarity in ?8 and d.cut in ?9
            """, nativeQuery = true)
    Page<Object[]> getDiamondWithPriceBy4C(String origin, List<String> shapeList, Double minPrice, Double maxPrice, Double minCarat, Double maxCarat, List<String> colorList, List<String> clarityList, List<String> cutList, Pageable pageable);
}
