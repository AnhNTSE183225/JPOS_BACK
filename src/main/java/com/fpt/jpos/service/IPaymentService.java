package com.fpt.jpos.service;

import com.fpt.jpos.dto.PaymentRestDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface IPaymentService {
    Double getPaidAmountByOrder(Integer orderId);

    PaymentRestDTO.VNPayResponse createVnPayPayment(HttpServletRequest request);
}
