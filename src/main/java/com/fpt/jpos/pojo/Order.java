package com.fpt.jpos.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpt.jpos.pojo.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`Order`")
@Data
@Builder
@NoArgsConstructor  // using Lombok
@AllArgsConstructor
public class Order {

    // declare entity fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "sale_staff_id")
    private Staff saleStaff;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "design_staff_id")
    private Staff designStaff;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "production_staff_id")
    private Staff productionStaff;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "budget")
    private String budget;

    @Column(name = "design_file")
    private String designFile;

    @Column(name = "description")
    private String description;

    @Column(name = "q_diamond_price")
    private Double qDiamondPrice;

    @Column(name = "q_material_price")
    private Double qMaterialPrice;

    @Column(name = "q_date")
    private Date qDate;

    @Column(name = "o_diamond_price")
    private Double oDiamondPrice;

    @Column(name = "o_material_price")
    private Double oMaterialPrice;

    @Column(name = "o_date")
    private Date oDate;

    @Column(name = "e_diamond_price")
    private Double eDiamondPrice;

    @Column(name = "e_material_price")
    private Double eMaterialPrice;

    @Column(name = "production_price")
    private Double productionPrice;

    @Column(name = "markup_rate")
    private Double markupRate;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "model_file")
    private String modelFile;

    @Column(name = "model_feedback")
    private String modelFeedback;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "shipping_fee")
    private Double shippingFee;

    @Column(name = "tax_fee")
    private Double taxFee;

    @Column(name = "discount")
    private Double discount;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    List<Payment> payments;
}
