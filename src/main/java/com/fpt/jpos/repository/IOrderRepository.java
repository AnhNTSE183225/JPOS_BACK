package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "SELECT * FROM [Order] WHERE [status] in ('wait_sale_staff','manager_approved','customer_accept','delivered') and sale_staff_id = ?1", nativeQuery = true)
    List<Order> findOrderForSalesStaff(Integer saleStaffId);

    @Query(value = "FROM Order WHERE customer.customerId = ?1 and (status = 'wait_customer' or status = 'pending_design')")
    List<Order> findOrdersForCustomer(Integer customerId);

    @Query(value = "SELECT * FROM [Order] WHERE status = 'designing' and design_staff_id = ?1 ", nativeQuery = true)
    List<Order> findOrdersForDesignStaff(Integer designStaffId);

    @Query(value = "SELECT * FROM [Order] WHERE status = 'production' and production_staff_id = ?1", nativeQuery = true)
    List<Order> findOrdersForProductionStaff(int productionStaffId);

    @Query(value= "SELECT * FROM [Order] WHERE status = 'completed'", nativeQuery=true)
    List<Order> findCompletedOrders();

    @Query(value = "SELECT * FROM [Order] WHERE status = 'wait_manager'", nativeQuery = true)
    List<Order> findOrdersForManager();
}
