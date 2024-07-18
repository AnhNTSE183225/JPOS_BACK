package com.fpt.jpos.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DesignConfigurations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DesignConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "config_id")
    private Integer configId;

    @Column(name = "design_type")
    private String designType;

    @Column(name = "config_info")
    private String configInfo;
}
