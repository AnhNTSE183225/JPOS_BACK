package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {
    //Optional<Order> findByCustomer(Order customer);

    @Query(value = "SELECT * FROM [Order] WHERE status != 'Completed' and (sale_staff_id = ?1 or sale_staff_id is null)", nativeQuery = true)
    List<Order> findAllByStatusAndStaff(int saleStaffId);

}
