package com.fpt.jpos.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fpt.jpos.pojo.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Diamond")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diamond {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diamond_id")
    private int diamondId;

    @Column(name = "diamond_code")
    private String diamondCode;

    @Column(name = "diamond_name")
    private String diamondName;

    @Enumerated(EnumType.STRING)
    @Column(name = "shape")
    private Shape shape;

    @Enumerated(EnumType.STRING)
    @Column(name = "origin")
    private Origin origin;

    @Column(name = "proportions")
    private String proportions;

    @Enumerated(EnumType.STRING)
    @Column(name = "fluorescence")
    private Fluorescence fluorescence;

    @Enumerated(EnumType.STRING)
    @Column(name = "symmetry")
    private Symmetry symmetry;

    @Enumerated(EnumType.STRING)
    @Column(name = "polish")
    private Polish polish;

    @Enumerated(EnumType.STRING)
    @Column(name = "cut")
    private Cut cut;

    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private Color color;

    @Enumerated(EnumType.STRING)
    @Column(name = "clarity")
    private Clarity clarity;

    @Column(name = "carat_weight")
    private double caratWeight;

    @Column(name = "note")
    private String note;

    @Column(name = "active")
    private Boolean active;

    @ManyToMany(mappedBy = "diamonds")
    @JsonIgnore
    List<Product> products = new ArrayList<>();
}
