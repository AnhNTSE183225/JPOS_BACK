package com.fpt.jpos.dto;

import lombok.Builder;


public abstract class PaymentRestDTO {
    @Builder
    public static class VNPayResponse {
        public String code;
        public String message;
        public String paymentUrl;
    }
}
