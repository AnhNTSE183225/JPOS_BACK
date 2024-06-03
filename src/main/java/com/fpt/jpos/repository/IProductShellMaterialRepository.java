package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.ProductShellMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductShellMaterialRepository extends JpaRepository<ProductShellMaterial, Integer> {
}
