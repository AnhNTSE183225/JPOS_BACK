package com.fpt.jpos.dto;

import lombok.Builder;

import java.util.Date;


public abstract class PaymentRestDTO {
    @Builder
    public static class VNPayResponse {
        public String code;
        public String message;
        public String paymentUrl;
    }

    @Builder
    public static class PaymentRequest {
        private Date paymentDate;
        private String paymentMethod;
        private String paymentStatus;
        private Double amountPaid;
        private Double amountTotal;
    }


}
