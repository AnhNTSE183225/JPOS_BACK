package com.fpt.jpos.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerRegistration {

    private String username;
    private String password;
    private String name;
    private String address;
}
