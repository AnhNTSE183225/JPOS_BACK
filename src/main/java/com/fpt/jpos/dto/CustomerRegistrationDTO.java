package com.fpt.jpos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerRegistrationDTO {

    private String username;
    private String password;
    private String name;
    private String address;
}
