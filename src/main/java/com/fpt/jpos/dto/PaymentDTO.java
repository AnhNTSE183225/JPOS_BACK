package com.fpt.jpos.dto;

import com.fpt.jpos.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private Date paymentDate;
    private String paymentMethod;
    private String paymentStatus;
    private Double amountPaid;
    private Double amountTotal;
}
