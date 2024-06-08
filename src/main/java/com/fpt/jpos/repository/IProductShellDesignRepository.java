package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.ProductShellDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductShellDesignRepository extends JpaRepository<ProductShellDesign, Integer> {

    @Query(value = "SELECT * FROM [ProductShellDesign] WHERE [product_design_id] = ?1", nativeQuery = true)
    List<ProductShellDesign> findByProductDesignId(Integer productDesignId);
}
