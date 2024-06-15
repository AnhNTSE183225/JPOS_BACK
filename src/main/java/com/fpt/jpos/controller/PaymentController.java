package com.fpt.jpos.controller;

import com.fpt.jpos.dto.PaymentRestDTO;
import com.fpt.jpos.service.IPaymentService;
import com.fpt.jpos.service.PaymentVNPayService;
import com.fpt.jpos.utils.response.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final IPaymentService paymentService;
    private final PaymentVNPayService vnPayService;

    @CrossOrigin
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getPaidAmountByOrderId(@PathVariable Integer orderId) {
        return ResponseEntity.ok(paymentService.getPaidAmountByOrder(orderId));
    }

    @GetMapping("/vn-pay")
    public ResponseObject<PaymentRestDTO.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", vnPayService.createVnPayPayment(request));
    }

    @GetMapping("/vn-pay-callback")
    public ResponseObject<PaymentRestDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return new ResponseObject<>(HttpStatus.OK, "Success", PaymentRestDTO.VNPayResponse.builder().
                    code("00")
                    .message("Success")
                    .paymentUrl("")
                    .build());
            //("00", "Success", ""));
        } else {
            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
        }
    }
}
