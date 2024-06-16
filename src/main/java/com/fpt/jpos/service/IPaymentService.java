package com.fpt.jpos.service;

import com.fpt.jpos.dto.PaymentRestDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface IPaymentService {
    public Double getPaidAmountByOrder(Integer orderId);

    PaymentRestDTO.VNPayResponse createVnPayPayment(HttpServletRequest request);
}
