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

    @Query(value="select [d].[diamond_id], [d].[diamond_code], [d].[diamond_name], [d].[shape], [d].[origin], [d].[proportions], [d].[fluorescence], [d].[symmetry], [d].[polish], [d].[cut], [d].[color], [d].[clarity], [d].[carat_weight], [d].[note], [d].[image], [d].[active], [pl].[price], [pl].[effective_date] from [Diamond] [d] join [DiamondPriceList] [pl] on [d].[origin] = [pl].[origin] and [d].[shape] = [pl].[shape] and [d].[carat_weight] = [pl].[carat_weight] and [d].[color] = [pl].[color] and [d].[clarity] = [pl].[clarity] and [d].[cut] = [pl].[cut] where [d].[carat_weight] >= (?1) and [d].[shape] in (?2) and [d].[color] in (?3) and [d].[cut] in (?4) and [d].[clarity] in (?5) and [pl].[price] >= (?6) and [pl].[effective_date] = (select top 1 max([effective_date]) from [DiamondPriceList] [pl2] where [pl2].[shape] = [pl].[shape] and [pl2].[origin] = [pl].[origin] and [pl2].[carat_weight] = [pl].[carat_weight] and [pl2].[color] = [pl].[color] and [pl2].[clarity] = [pl].[clarity] and [pl2].[cut] = [pl].[cut])",nativeQuery = true)
    Page<Diamond> diamondQuery(Double caratWeight, List<String> shape, List<String> color, List<String> cut, List<String> clarity, Double price, Pageable pageable);
}
