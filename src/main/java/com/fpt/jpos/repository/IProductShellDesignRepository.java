package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.ProductShellDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductShellDesignRepository extends JpaRepository<ProductShellDesign, Integer> {
}
