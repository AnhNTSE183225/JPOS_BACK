package com.fpt.jpos.controller;

import com.fpt.jpos.dto.PaymentRestDTO;
import com.fpt.jpos.service.IPaymentService;
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

    @CrossOrigin
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getPaidAmountByOrderId(@PathVariable Integer orderId) {
        return ResponseEntity.ok(paymentService.getPaidAmountByOrder(orderId));
    }

    @GetMapping("/vn-pay")
    public ResponseObject<PaymentRestDTO.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }


    // Trên frontend if code == 00 thì gọi api như luồng kia
    @GetMapping("/vn-pay-callback")
    public ResponseObject<PaymentRestDTO.VNPayResponse> payCallbackHandler(@RequestParam String vnp_ResponseCode
    ) {
        if (vnp_ResponseCode.equals("00")) {
            return new ResponseObject<>(HttpStatus.OK, "Success", PaymentRestDTO.VNPayResponse.builder().
                    code("00")
                    .message("Success")
                    .paymentUrl("")
                    .build());
        } else {
            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
        }
    }
}
