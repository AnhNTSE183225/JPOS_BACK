package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.MaterialPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface IMaterialPriceRepository extends JpaRepository<MaterialPrice, Integer> {

    @Query("SELECT mp FROM MaterialPrice mp WHERE mp.materialPriceId.materialId = ?1")
    List<MaterialPrice> findMaterialPriceByMaterialId(Integer id);

    @Modifying
    @Query(value = "INSERT INTO MaterialPriceList (material_id, effective_date, price) VALUES (?1, ?2, ?3)", nativeQuery = true)
    @Transactional
    int addMaterialPrice(Integer materialId, Date effectiveDate, Double price);

    @Modifying
    @Query(value = "UPDATE MaterialPriceList SET price = ?1 WHERE material_id = ?2 and effective_date = ?3", nativeQuery = true)
    @Transactional
    int updateMaterialPrice(Double price, Integer id, Date effectiveDate);
}
