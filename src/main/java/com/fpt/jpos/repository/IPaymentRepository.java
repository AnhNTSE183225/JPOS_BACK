package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {

    @Query("from Payment where order.id = ?1")
    Payment findPaymentByOrderId(Integer orderId);

    @Query(value = """
            SELECT
                CAST([payment_date] AS DATE) AS [payment_date],
                SUM([amount_paid]) AS [total_amount_paid]
            FROM
                [Payment]
            GROUP BY
                CAST([payment_date] AS DATE)
            ORDER BY [payment_date];
            """,nativeQuery = true)
    List<Object[]> getPaymentByDates();
}
