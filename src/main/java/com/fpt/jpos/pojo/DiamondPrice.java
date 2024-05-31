package com.fpt.jpos.pojo;

import com.fpt.jpos.pojo.enums.Clarity;
import com.fpt.jpos.pojo.enums.Color;
import com.fpt.jpos.pojo.enums.Cut;
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
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(name = "clarity")
    @Enumerated(EnumType.STRING)
    private Clarity clarity;

    @Column(name = "cut")
    @Enumerated(EnumType.STRING)
    private Cut cut;

    @Column(name = "price")
    private Double price;

    @Column(name = "effective_date")
    private Date effectiveDate;
}
