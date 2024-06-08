package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.ProductMaterial;
import com.fpt.jpos.pojo.ProductMaterialId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductMaterialRepository extends JpaRepository<ProductMaterial, ProductMaterialId> {
}
