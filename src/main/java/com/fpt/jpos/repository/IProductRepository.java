package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * FROM Product WHERE product_type = 'ring'",nativeQuery = true)
    List<Product> getRingProducts();

    @Query(value = "SELECT * FROM Product WHERE product_type = 'necklace'",nativeQuery = true)
    List<Product> getNecklaceProducts();

    @Query(value = "SELECT * FROM Product WHERE product_type = 'earrings'",nativeQuery = true)
    List<Product> getEarringsProducts();

    @Query(value = "SELECT * FROM Product WHERE product_type = 'bracelets'",nativeQuery = true)
    List<Product> getBraceletsProducts();
}
