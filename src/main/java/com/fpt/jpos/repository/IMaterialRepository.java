package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMaterialRepository extends JpaRepository<Material, Integer> {
}
