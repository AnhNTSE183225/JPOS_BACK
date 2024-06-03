package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Diamond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiamondRepository extends JpaRepository<Diamond, Integer> {
    @Query(value = "Select * from [Diamond] where carat_weight >= ?1 and carat_weight <= ?2 and clarity = ?3 and color = ?4 and cut = ?5 and shape = ?6", nativeQuery = true)
    List<Diamond> findDiamondsBy4C(Double fromCaratWeight, Double toCaratWeight, String clarity, String color, String cut, String shape);
}
