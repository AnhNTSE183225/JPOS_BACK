package com.fpt.jpos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StaffRegistrationDTO {
    private String username;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String staffType;
}
