package com.fpt.jpos.controller;

import com.fpt.jpos.dto.PaymentRestDTO;
import com.fpt.jpos.service.IOrderService;
import com.fpt.jpos.service.IPaymentService;
import com.fpt.jpos.utils.response.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@CrossOrigin
public class PaymentController {

    private final IPaymentService paymentService;
    private final IOrderService orderService;

    @GetMapping("/find-all")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> findAll() {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(paymentService.findAll());
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return response;
    }

    //Binh
    @GetMapping("/{orderId}")
    @PreAuthorize("hasAuthority('customer') or hasAuthority('staff') or hasAuthority('admin')")
    public ResponseEntity<?> getPaidAmountByOrderId(@PathVariable Integer orderId) {
        return ResponseEntity.ok(paymentService.getPaidAmountByOrder(orderId));
    }

    //Binh
    @GetMapping("/vn-pay")
    @PreAuthorize("hasAuthority('customer') or hasAuthority('staff') or hasAuthority('admin')")
    public ResponseObject<PaymentRestDTO.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }

    // Trên frontend if code == 00 thì gọi api như luồng kia
    //Binh
    @GetMapping("/vn-pay-callback")
    @PreAuthorize("hasAuthority('customer') or hasAuthority('staff') or hasAuthority('admin')")
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

    @GetMapping("/info/{orderId}")
    @PreAuthorize("hasAnyAuthority('customer','staff','admin')")
    public ResponseEntity<?> findPaymentByOrderId(@PathVariable Integer orderId) {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(this.paymentService.findPaymentByOrderId(orderId));
        } catch (Exception ex) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }
}
