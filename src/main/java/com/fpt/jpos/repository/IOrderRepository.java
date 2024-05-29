package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.pojo.Order;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByCustomer(Customer customer);
}
