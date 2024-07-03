package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStaffRepository extends JpaRepository<Staff, Integer> {

    List<Staff> findByStaffType(String staffType);

    @Query(value = "select * from [Staff] s where s.[username] = ?1 and ?2 = (select a.[password] from [Account] a where s.[username] = a.[username])", nativeQuery = true)
    Staff findByUsernameAndPassword(String username, String password);

    @Modifying
    @Query(value = "UPDATE Staff SET name = ?1, phone = ?2, staff_type = ?3 WHERE staff_id = ?4", nativeQuery = true)
    int updateStaff(String name, String phone, String staffType, Integer staffId);
}
