package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.ProductShellMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductShellMaterialRepository extends JpaRepository<ProductShellMaterial, Integer> {

    @Query(value = "SELECT * FROM [ProductShellMaterial] WHERE [shell_id] = ?1", nativeQuery = true)
    List<ProductShellMaterial> findByShellId(Integer shellId);
}
