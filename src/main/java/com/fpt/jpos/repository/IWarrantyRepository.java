package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Warranty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWarrantyRepository extends JpaRepository<Warranty, Integer> {
}
