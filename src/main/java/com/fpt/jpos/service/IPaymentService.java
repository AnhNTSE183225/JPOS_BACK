package com.fpt.jpos.service;

import com.fpt.jpos.dto.PaymentRestDTO;
import com.fpt.jpos.pojo.Payment;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IPaymentService {
    Double getPaidAmountByOrder(Integer orderId);

    PaymentRestDTO.VNPayResponse createVnPayPayment(HttpServletRequest request);

    List<Payment> findAll();

    Payment findPaymentByOrderId(Integer orderId) throws Exception;
}
