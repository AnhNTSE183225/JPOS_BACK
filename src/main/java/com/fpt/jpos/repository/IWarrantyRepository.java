package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IWarrantyRepository extends JpaRepository<Warranty, Integer> {

    @Query(value = """
            SELECT *
            FROM Warranty
            WHERE product_id = ?1
            """,nativeQuery = true)
    Warranty findByProductId(Integer productId);
}
