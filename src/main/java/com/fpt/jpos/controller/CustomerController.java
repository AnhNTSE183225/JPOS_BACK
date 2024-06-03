package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.dto.CustomerRegistrationDTO;
import com.fpt.jpos.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @CrossOrigin
    @PostMapping("/customer-login")
    public ResponseEntity<Customer> login(@RequestBody Account account) {
        Customer customer = customerService.loginCustomer(account);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(customer);
        }
    }

    @CrossOrigin
    @PostMapping("/customer-register")
    public ResponseEntity<Customer> customerRegister(@RequestBody CustomerRegistrationDTO customerRegistrationDTO) {
        Customer newCustomer = customerService.registerCustomer(customerRegistrationDTO);
        if(newCustomer != null) {
            return ResponseEntity.ok(newCustomer);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
