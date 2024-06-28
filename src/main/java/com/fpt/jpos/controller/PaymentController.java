package com.fpt.jpos.controller;

import com.fpt.jpos.dto.PaymentRestDTO;
import com.fpt.jpos.service.IOrderService;
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
    private final IOrderService orderService;

    @CrossOrigin
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getPaidAmountByOrderId(@PathVariable Integer orderId) {
        return ResponseEntity.ok(paymentService.getPaidAmountByOrder(orderId));
    }

    @CrossOrigin
    @GetMapping("/vn-pay")
    public ResponseObject<PaymentRestDTO.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }


    // Trên frontend if code == 00 thì gọi api như luồng kia
    @CrossOrigin
    @GetMapping("/vn-pay-callback")
    public ResponseObject<PaymentRestDTO.VNPayResponse> payCallbackHandler(@RequestParam String vnp_ResponseCode, @RequestParam Integer orderId, @RequestParam String orderType
    ) {
        if (vnp_ResponseCode.equals("00")) {
            orderService.confirmPaymentSuccess(orderId, orderType);
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
