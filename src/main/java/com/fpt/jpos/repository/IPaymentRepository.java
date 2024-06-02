package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("from Payment where order.id = ?1")
    Payment findPaymentByOrderId(Integer orderId);

}
