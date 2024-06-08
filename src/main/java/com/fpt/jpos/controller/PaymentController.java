package com.fpt.jpos.controller;

import com.fpt.jpos.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private final IPaymentService paymentService;

    @Autowired
    public PaymentController(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @CrossOrigin
    @GetMapping("/payment/{orderId}")
    public ResponseEntity<?> getPaidAmountByOrderId(@PathVariable Integer orderId) {
        return ResponseEntity.ok(paymentService.getPaidAmountByOrder(orderId));
    }
}
