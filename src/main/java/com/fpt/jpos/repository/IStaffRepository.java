package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStaffRepository extends JpaRepository<Staff, Integer> {

    List<Staff> findByStaffType(String staffType);

    @Query(value = "select * from [Staff] s where s.[username] = ?1 and ?2 = (select a.[password] from [Account] a where s.[username] = a.[username])", nativeQuery = true)
    Staff findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT * FROM [Staff] WHERE username = ?1",nativeQuery = true)
    Staff findStaffByUsername(String username);
}
