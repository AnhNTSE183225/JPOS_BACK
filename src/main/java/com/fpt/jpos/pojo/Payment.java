package com.fpt.jpos.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Payment")
@Data
@NoArgsConstructor  // using lombok
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "payment_date")
    private Date paymentDate;
    @Column(name = "payment_method")
    private String payment_method;
    @Column(name = "payment_status")
    private String payment_status;
    @Column(name = "amount_paid")
    private Double amount_paid;
    @Column(name = "amount_total")
    private Double amount_total;

}
