package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Staff;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStaffRepository extends JpaRepository<Staff, Integer> {
    public Staff findByUsername(String username);
    List<Staff> findByStaffType(String staffType);

}
