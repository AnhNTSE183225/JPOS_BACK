package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Payment;
import com.fpt.jpos.repository.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService implements IPaymentService {

    private final IPaymentRepository paymentRepository;

    @Autowired
    public PaymentService(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Double getPaidAmountByOrder(Integer orderId) {
        Payment payment = paymentRepository.findPaymentByOrderId(orderId);

        Double amount = 0.0;
        if (payment != null) {
            amount = payment.getAmountPaid();
        }

        return amount;
    }
}
