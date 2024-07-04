package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CustomerController {
    private final ICustomerService customerService;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('customer','admin','staff')")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        ResponseEntity<?> responseEntity = ResponseEntity.noContent().build();

        try {
            responseEntity = ResponseEntity.ok(customerService.updateCustomer(customer));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return responseEntity;
    }
}
