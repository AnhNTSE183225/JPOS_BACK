package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.pojo.Staff;
import com.fpt.jpos.service.ICustomerService;
import com.fpt.jpos.service.IStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginController {

    private final ICustomerService customerService;
    private final IStaffService staffService;

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        Customer customer = customerService.loginCustomer(account);
        Staff staff = staffService.getStaffByAccount(account);

        if(customer != null) {
            return ResponseEntity.ok(customer);
        } else if(staff != null) {
            return ResponseEntity.ok(staff);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
