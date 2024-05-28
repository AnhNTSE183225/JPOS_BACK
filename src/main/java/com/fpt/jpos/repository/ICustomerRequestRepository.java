package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRequestRepository extends JpaRepository<Order, Integer> {
}
