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

    @Query(value = """

                        SELECT d.*, dp.price, dp.latest_date\s
            FROM Diamond d
            LEFT JOIN
            (
                SELECT p.origin, p.shape, p.carat_weight_from, p.carat_weight_to, p.color, p.clarity, p.cut, p.price, pp.latest_date\s
                FROM DiamondPriceList p
                INNER JOIN
                (
                    SELECT origin, shape, carat_weight_from, carat_weight_to, color, clarity, cut, MAX(effective_date) as latest_date\s
                    FROM DiamondPriceList
                    WHERE effective_date <= GETDATE()
                    GROUP BY origin, shape, carat_weight_from, carat_weight_to, color, clarity, cut
                ) pp
                ON p.effective_date = pp.latest_date\s
                AND p.origin = pp.origin\s
                AND p.shape = pp.shape\s
                AND p.carat_weight_from = pp.carat_weight_from\s
                AND p.carat_weight_to = pp.carat_weight_to\s
                AND p.color = pp.color\s
                AND p.cut = pp.cut\s
                AND p.clarity = pp.clarity
            ) dp
            ON d.origin = dp.origin\s
            AND d.shape = dp.shape\s
            AND d.carat_weight > dp.carat_weight_from\s
            AND d.carat_weight <= dp.carat_weight_to\s
            AND d.color = dp.color\s
            AND d.clarity = dp.clarity\s
            AND d.cut = dp.cut            
            where d.origin = ?1 and d.shape in ?2 and dp.price >= ?3 and dp.price <= ?4 and d.carat_weight >= ?5 and d.carat_weight <= ?6 and d.color in ?7 and d.clarity in ?8 and d.cut in ?9 and d.active = 1
                        """, nativeQuery = true)
    Page<Object[]> getDiamondWithPriceBy4C(String origin, List<String> shapeList, Double minPrice, Double maxPrice, Double minCarat, Double maxCarat, List<String> colorList, List<String> clarityList, List<String> cutList, Pageable pageable);
}
