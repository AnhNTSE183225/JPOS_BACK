package com.fpt.jpos.service;

import com.fpt.jpos.config.VNPAYConfig;
import com.fpt.jpos.dto.PaymentRestDTO;
import com.fpt.jpos.pojo.Payment;
import com.fpt.jpos.repository.IPaymentRepository;
import com.fpt.jpos.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

    private final IPaymentRepository paymentRepository;

    private final VNPAYConfig vnPayConfig;

    @Override
    public Double getPaidAmountByOrder(Integer orderId) {
        Payment payment = paymentRepository.findPaymentByOrderId(orderId);

        Double amount = 0.0;
        if (payment != null) {
            amount = payment.getAmountPaid();
        }

        return amount;
    }

    @Override
    //Binh
    public PaymentRestDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
        double d = Double.parseDouble(request.getParameter("amount")) * 25455.50; //Conversion rate
        int i = (int) Math.ceil(d);
        long l = i * 100L;
        String s = String.valueOf(l);

        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", s);
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentRestDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment findPaymentByOrderId(Integer orderId) throws Exception {
        Payment p = this.paymentRepository.findPaymentByOrderId(orderId);
        if(p == null) {
            throw new Exception("Payment not found");
        }
        return p;
    }
}
