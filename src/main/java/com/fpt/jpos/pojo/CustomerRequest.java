package com.fpt.jpos.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerRequest {
    private int customerId;
    private String designFile;
    private String description;
    private String budget;
}
