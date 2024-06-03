package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.ProductDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IProductDesignRepository extends JpaRepository<ProductDesign, Integer> {
}
