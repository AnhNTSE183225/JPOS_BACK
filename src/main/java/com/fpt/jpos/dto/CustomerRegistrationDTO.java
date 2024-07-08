package com.fpt.jpos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegistrationDTO {
    private String username;
    private String email;
    private String password;
    private String name;
    private String address;
}
