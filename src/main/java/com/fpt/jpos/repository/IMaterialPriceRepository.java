package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.MaterialPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMaterialPriceRepository extends JpaRepository<MaterialPrice, Integer> {

    @Query("SELECT mp FROM MaterialPrice mp WHERE mp.materialPriceId.materialId = ?1")
    List<MaterialPrice> findMaterialPriceByMaterialId(Integer id);
}
