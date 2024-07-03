package com.fpt.jpos.controller;

import com.fpt.jpos.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('customer') or hasAuthority('admin')")
    public ResponseEntity<?> updateCustomer(@RequestParam Integer customerId, @RequestParam String email, @RequestParam String name, @RequestParam String address) {
        ResponseEntity<?> responseEntity = ResponseEntity.noContent().build();

        try {
            responseEntity = ResponseEntity.ok(customerService.updateCustomer(customerId, name, email, address));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return responseEntity;
    }
}
