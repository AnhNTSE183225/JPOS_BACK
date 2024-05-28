package com.fpt.jpos.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "DiamondPriceList")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiamondPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diamond_price_id")
    private Integer diamondPriceId;

    @Column(name = "origin")
    private String origin;

    @Column(name = "carat_weight")
    private Double caratWeight;

    @Column(name = "color")
    private String color;

    @Column(name = "clarity")
    private String clarity;

    @Column(name = "cut")
    private String cut;

    @Column(name = "price")
    private Double price;

    @Column(name = "effective_date")
    private Date effectiveDate;
}
